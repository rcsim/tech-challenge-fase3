package com.postech30.parkingmeter.service.impl;

import com.postech30.parkingmeter.controller.TicketController;
import com.postech30.parkingmeter.dto.EmailDTO;
import com.postech30.parkingmeter.dto.TicketDTO;
import com.postech30.parkingmeter.dto.UserDTO;
import com.postech30.parkingmeter.entity.Card;
import com.postech30.parkingmeter.entity.Ticket;
import com.postech30.parkingmeter.entity.User;
import com.postech30.parkingmeter.entity.Vehicle;
import com.postech30.parkingmeter.exceptions.BadRequestException;
import com.postech30.parkingmeter.exceptions.ResourceNotFoundException;
import com.postech30.parkingmeter.repository.*;
import com.postech30.parkingmeter.service.EmailService;
import com.postech30.parkingmeter.service.TicketService;
import com.postech30.parkingmeter.service.UserService;
import com.postech30.parkingmeter.util.PDFGenerator;
import com.postech30.parkingmeter.util.pix.PixGenerator;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
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
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JobScheduler jobScheduler;

    private Ticket mapTo(Long vehicleId, Long userId, Instant in, Instant out, String status, Double price, int paymentType,
                         Long cardID, String pixCode, Ticket entity) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new ResourceNotFoundException("Não é possível criar um ticket para um veículo inexistente."));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Não é possível criar um ticket para umusuário inexistente."));

        Card card = null;
        if(cardID != null){
            card  = cardRepository.findById(cardID).orElseThrow(
                    () -> new ResourceNotFoundException("Não é possível criar um ticket para cartão inexistente."));
        }


        entity.setVehicle(vehicle);
        entity.setUser(user);
        entity.setCheckIn(in);
        entity.setCheckOut(out);
        entity.setStatus(status);
        entity.setPaymentType(paymentType);
        entity.setCard(card);
        entity.setPixCode(pixCode);
        entity.setPrice(price);
        return entity;
    }

    @Override
    @Transactional
    public @Valid TicketDTO checkIn(TicketDTO ticketDTO) {
        Ticket ticketEntity = new Ticket();
        Long parkingHours =  ticketDTO.getParkingHours();
        Double hourPrice = priceRepository.findLastHourPrice();
        Instant checkOut = null;
        Double price = null;
        String pixCode = null;


        if (ticketDTO.getPaymentType() == 1) {
            if (parkingHours <= 0L){
                throw new BadRequestException("O número de horas é necessário para tickets com pagamento por PIX!!!");
            }
            checkOut = Instant.now().plusSeconds(parkingHours*3600);
            price = parkingHours*hourPrice;
            pixCode = generatePixCode(price);
        }

        if(ticketDTO.getPaymentType() == 0 && ticketDTO.getCardId() == null )
        {
            throw new BadRequestException("Meio de pagamento necessário para tickets com pagamento por hora!!!");
        }

        ticketEntity = mapTo(ticketDTO.getVehicleId(), ticketDTO.getUserId(), Instant.now(), checkOut, "open", price,
                ticketDTO.getPaymentType(), ticketDTO.getCardId(), pixCode, ticketEntity);

        TicketDTO newTicket = new TicketDTO(ticketRepository.save(ticketEntity));

        if (ticketDTO.getPaymentType() == 1){
            jobScheduler.schedule(LocalDateTime.now().plusSeconds(3600*parkingHours), () -> closePendingTicketJob(newTicket.getId()));
        }
        return newTicket;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketDTO> searchOpenTickets() {
        List<Ticket> list = ticketRepository.findByCheckOutIsNullOrCheckOutGreaterThanEqual(Instant.now());
        return list.stream().map(x -> new TicketDTO(x, calcParkingHours(x))).collect(Collectors.toList());
    }

    private Long calcParkingHours(Ticket ticket) {
        return Duration.between( ticket.getCheckIn(), Instant.now()).getSeconds();
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

        if(ticket.getPaymentType() == 1  &&  !ticket.getStatus().equals("closed")){
            ticket.setStatus("closed");
            return new TicketDTO(ticketRepository.save(ticket));
        }
        else if (ticket.getCheckOut() != null) {
            throw new BadRequestException("Não é possível fazer o checkout desse ticket.");
        }
        Long deltaInSeconds =  calcParkingHours(ticket);
        Long  parkingHours = (long) Math.ceil((double)deltaInSeconds/3600);
        Double hourPrice = priceRepository.findLastHourPrice();
        Double price =  parkingHours*hourPrice;


        ticket = mapTo(ticket.getVehicle().getId(),  ticket.getUser().getId(), ticket.getCheckIn(), Instant.now(), "closed", price,
                ticket.getPaymentType(), ticket.getCard().getId(), null, ticket);

        Ticket jobTicket = ticket;

        jobScheduler.enqueue( () -> sendEmailJob(jobTicket.getVehicle().getId(), jobTicket.getVehicle().getPlate(),
                jobTicket.getCheckIn(),jobTicket.getCheckOut(), price));

        return new TicketDTO(ticketRepository.save(ticket), parkingHours);
    }

    private void enviarComprovante(Long id) throws MessagingException {
        UserDTO user = userService.findById(id);
        emailService.sendMailWithAttachment(new EmailDTO(user.getEmail(), user.getEmail()));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Job(name = "Close pending Ticket Job", retries = 2)
    public void closePendingTicketJob(Long id) throws IOException, MessagingException {
        this.checkOut(id);
    }

    @Job(name = "Send Email Job", retries = 2)
    public void sendEmailJob( Long vehicleId, String plate , Instant checkin, Instant checkout, Double price) throws IOException, MessagingException {
        PDFGenerator.generatePDFFromHTML(plate , checkin, checkout,price);
        enviarComprovante(vehicleId);
    }
}
