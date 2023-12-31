package com.postech30.parkingmeter.service;

import com.postech30.parkingmeter.dto.TicketDTO;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

public interface TicketService {

    @Valid TicketDTO checkIn(TicketDTO ticketDTO);

    List<TicketDTO> searchOpenTickets();

    @Valid TicketDTO checkOut(Long id) throws IOException, MessagingException;
}
