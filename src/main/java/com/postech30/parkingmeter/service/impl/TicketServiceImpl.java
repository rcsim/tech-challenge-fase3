package com.postech30.parkingmeter.service.impl;

import com.postech30.parkingmeter.dto.TicketDTO;
import com.postech30.parkingmeter.entity.Ticket;
import com.postech30.parkingmeter.exceptions.ResourceNotFoundException;
import com.postech30.parkingmeter.repository.TicketRepository;
import com.postech30.parkingmeter.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;


    private Ticket mapTo(Long vehicleId, Instant in, Instant out, Ticket entity) {
        entity.setVehicleId(vehicleId);
        entity.setCheckIn(in);
        entity.setCheckOut(out);
        return entity;
    }

    @Override
    @Transactional
    public @Valid TicketDTO checkIn(TicketDTO ticketDTO) {
        Ticket ticketEntity = new Ticket();
        ticketEntity = mapTo(ticketDTO.getVehicleId(), Instant.now(), null, ticketEntity);
        return new TicketDTO(ticketRepository.save(ticketEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketDTO> searchOpenTickets() {
        List<Ticket> list = ticketRepository.findByCheckOut(null);
        return list.stream().map(TicketDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public @Valid TicketDTO checkOut(TicketDTO ticketDTO) {
        long id = ticketDTO.getId();
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket não encontrado");
        }

        Ticket ticket = ticketRepository.getReferenceById(id);
        ticket = mapTo(ticket.getVehicleId(), ticket.getCheckIn(), Instant.now(), ticket);
        return new TicketDTO(ticketRepository.save(ticket));
    }
}
