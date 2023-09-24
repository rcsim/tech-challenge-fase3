package com.postech30.parkingmeter.service.impl;

import com.postech30.parkingmeter.dto.TicketDTO;
import com.postech30.parkingmeter.entity.Ticket;
import com.postech30.parkingmeter.entity.Vehicle;
import com.postech30.parkingmeter.exceptions.ResourceNotFoundException;
import com.postech30.parkingmeter.repository.TicketRepository;
import com.postech30.parkingmeter.repository.VehicleRepository;
import com.postech30.parkingmeter.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private Ticket mapTo(Long vehicleId, Instant in, Instant out, Ticket entity) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new ResourceNotFoundException("Não é possível criar um ticket para um veículo inexistente."));
        entity.setVehicle(vehicle);
        entity.setCheckIn(in);
        entity.setCheckOut(out);
        return entity;
    }

    @Override
    @Transactional
    public @Valid TicketDTO checkIn(TicketDTO ticketDTO) {
        Ticket ticketEntity = new Ticket();
        Long parkingHours =  ticketDTO.getParkingHours();
        Instant checkOut = null;

        if (parkingHours > 0L) {
            checkOut = Instant.now().plusSeconds(parkingHours*3600);
        }

        ticketEntity = mapTo(ticketDTO.getVehicleId(), Instant.now(), checkOut, ticketEntity);
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
    public @Valid TicketDTO checkOut(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket não encontrado");
        }

        Ticket ticket = ticketRepository.getReferenceById(id);

        if (ticket.getCheckOut() != null) {
            throw new ResourceNotFoundException("Não foi encontrado ticket aberto com esse Id");
        }

        ticket = mapTo(ticket.getVehicle().getId(), ticket.getCheckIn(), Instant.now(), ticket);
        return new TicketDTO(ticketRepository.save(ticket), calcParkingHours(ticket));
    }
}
