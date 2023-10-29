package com.postech30.parkingmeter.service.impl;

import com.postech30.parkingmeter.dto.EmailDTO;
import com.postech30.parkingmeter.dto.TicketDTO;
import com.postech30.parkingmeter.dto.UserDTO;
import com.postech30.parkingmeter.entity.Ticket;
import com.postech30.parkingmeter.entity.Vehicle;
import com.postech30.parkingmeter.exceptions.BadRequestException;
import com.postech30.parkingmeter.exceptions.ResourceNotFoundException;
import com.postech30.parkingmeter.repository.TicketRepository;
import com.postech30.parkingmeter.repository.VehicleRepository;
import com.postech30.parkingmeter.repository.PriceRepository;
import com.postech30.parkingmeter.service.EmailService;
import com.postech30.parkingmeter.service.TicketService;
import com.postech30.parkingmeter.service.UserService;
import com.postech30.parkingmeter.util.PDFGenerator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

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

    private Ticket mapTo(Long vehicleId, Instant in, Instant out, String status, Double price, Ticket entity) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new ResourceNotFoundException("Não é possível criar um ticket para um veículo inexistente."));
        entity.setVehicle(vehicle);
        entity.setCheckIn(in);
        entity.setCheckOut(out);
        entity.setStatus(status);
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

        if (parkingHours > 0L) {
            checkOut = Instant.now().plusSeconds(parkingHours*3600);
        }

        ticketEntity = mapTo(ticketDTO.getVehicleId(), Instant.now(), checkOut, "open", parkingHours*hourPrice, ticketEntity);
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

    @Override
    @Transactional
    public @Valid TicketDTO checkOut(Long id) throws IOException {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket não encontrado");
        }

        Ticket ticket = ticketRepository.getReferenceById(id);

        if (ticket.getCheckOut() != null) {
            throw new BadRequestException("Não é possível fazer o checkout desse ticket.");
        }
        Long parkingHours =  calcParkingHours(ticket);
        Double hourPrice = priceRepository.findLastHourPrice();

        ticket = mapTo(ticket.getVehicle().getId(), ticket.getCheckIn(), Instant.now(), "closed", parkingHours*hourPrice, ticket);
        PDFGenerator.generatePDFFromHTML(ticket.getVehicle().getPlate(),ticket.getCheckIn(),ticket.getCheckOut(),parkingHours*hourPrice);
        enviarComprovante(ticket.getVehicle().getId());
        return new TicketDTO(ticketRepository.save(ticket), parkingHours);
    }

    private void enviarComprovante(Long id) {

        List<UserDTO> users = userService.findUserByVehicleId(id);
        users.forEach(user -> {
            emailService.sendMail(new EmailDTO(user.getEmail(), user.getEmail()));
        });
    }
}
