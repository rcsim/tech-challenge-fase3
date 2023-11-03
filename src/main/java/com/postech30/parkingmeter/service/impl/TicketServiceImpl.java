package com.postech30.parkingmeter.service.impl;

import com.postech30.parkingmeter.dto.EmailDTO;
import com.postech30.parkingmeter.dto.TicketDTO;
import com.postech30.parkingmeter.dto.UserDTO;
import com.postech30.parkingmeter.entity.Ticket;
import com.postech30.parkingmeter.entity.User;
import com.postech30.parkingmeter.entity.Vehicle;
import com.postech30.parkingmeter.exceptions.BadRequestException;
import com.postech30.parkingmeter.exceptions.ResourceNotFoundException;
import com.postech30.parkingmeter.repository.TicketRepository;
import com.postech30.parkingmeter.repository.UserRepository;
import com.postech30.parkingmeter.repository.VehicleRepository;
import com.postech30.parkingmeter.repository.PriceRepository;
import com.postech30.parkingmeter.service.EmailService;
import com.postech30.parkingmeter.service.TicketService;
import com.postech30.parkingmeter.service.UserService;
import com.postech30.parkingmeter.util.PDFGenerator;
import com.postech30.parkingmeter.util.pix.PixGenerator;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.postech30.parkingmeter.util.pix.PixData.*;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    private Ticket mapTo(Long vehicleId, Long userId, Instant in, Instant out, String status, Double price, int paymentType,
                         Long cardID, String pixCode, Ticket entity) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new ResourceNotFoundException("Não é possível criar um ticket para um veículo inexistente."));
        entity.setVehicle(vehicle);
        entity.setUserId(userId);
        entity.setCheckIn(in);
        entity.setCheckOut(out);
        entity.setStatus(status);
        entity.setPaymentType(paymentType);
        entity.setCardId(cardID);
        entity.setPixCode(pixCode);
        entity.setPrice(price);
        return entity;
    }

    @Override
    @Transactional
    public @Valid TicketDTO checkIn(TicketDTO ticketDTO) {
        Ticket ticketEntity = new Ticket();
        Long parkingHours =  ticketDTO.getParkingHours();
        Instant checkOut = null;
        Double hourPrice = priceRepository.findLastHourPrice();

        if(ticketDTO.getPaymentType() == 0 && ticketDTO.getCardId() == null )
        {
            throw new BadRequestException("Meio de pagamento necessário para tickets com pagamento por hora!!!");
        }

        ticketEntity = mapTo(ticketDTO.getVehicleId(), ticketDTO.getUserId(), Instant.now(), checkOut, "open", parkingHours*hourPrice,
                ticketDTO.getPaymentType(), ticketDTO.getCardId(), null, ticketEntity);
        return new TicketDTO(ticketRepository.save(ticketEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketDTO> searchOpenTickets() {
        List<Ticket> list = ticketRepository.findByCheckOutIsNullOrCheckOutGreaterThanEqual(Instant.now());
        return list.stream().map(x -> new TicketDTO(x, calcParkingHours(x))).collect(Collectors.toList());
    }

    private Long calcParkingHours(Ticket ticket) {
        return Duration.between( ticket.getCheckIn(), Instant.now()).toHours();
    }

    private String generatePixCode(Double price){
        PixGenerator pixGenerator = new PixGenerator();
        pixGenerator.setReceiverFullName(RECEIVER_NAME);
        pixGenerator.setPixKey(PIX_KEY);
        pixGenerator.setReceiverCity(CITY);
        pixGenerator.setTransactionIdentifier(IDENTIFIER);
        pixGenerator.setWithValue(true);
        pixGenerator.setValue(price);
        return pixGenerator.getAsText();
    }

    @Override
    @Transactional
    public @Valid TicketDTO checkOut(Long id) throws IOException, MessagingException {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket não encontrado");
        }

        Ticket ticket = ticketRepository.getReferenceById(id);
        String pixCode = null;

        if (ticket.getCheckOut() != null) {
            throw new BadRequestException("Não é possível fazer o checkout desse ticket.");
        }
        Long parkingHours =  calcParkingHours(ticket);
        Double hourPrice = priceRepository.findLastHourPrice();
        Double price = parkingHours*hourPrice;

        if(ticket.getPaymentType() == 1){
            pixCode = generatePixCode(price);
        }
        ticket = mapTo(ticket.getVehicle().getId(),  ticket.getUserId(), ticket.getCheckIn(), Instant.now(), "closed", price,
                ticket.getPaymentType(), ticket.getCardId(), pixCode, ticket);
        PDFGenerator.generatePDFFromHTML(ticket.getVehicle().getPlate(),ticket.getCheckIn(),ticket.getCheckOut(),price);
        enviarComprovante(ticket.getUserId());

        return new TicketDTO(ticketRepository.save(ticket), parkingHours);
    }

    private void enviarComprovante(Long id) throws MessagingException {

        UserDTO user = userService.findById(id);
        emailService.sendMailWithAttachment(new EmailDTO(user.getEmail(), user.getEmail()));
    }
}
