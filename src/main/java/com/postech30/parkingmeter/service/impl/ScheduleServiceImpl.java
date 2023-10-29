package com.postech30.parkingmeter.service.impl;

import com.postech30.parkingmeter.dto.EmailDTO;
import com.postech30.parkingmeter.dto.TicketDTO;
import com.postech30.parkingmeter.service.EmailService;
import com.postech30.parkingmeter.service.ScheduleService;
import com.postech30.parkingmeter.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class ScheduleServiceImpl  implements ScheduleService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private TicketService ticketService;


    @Scheduled(initialDelay = 1000,fixedDelay =60000 )
    @Override
    public void sendNotification() {
        List<TicketDTO> listaTicket =  ticketService.searchOpenTickets();

        listaTicket.forEach(ticket -> {
            if (ticket.getParkingHours() <= 1)
                emailService.sendMail(new EmailDTO("marcosfernandesalves@gmail.com","marcosfernandesalves@gmail.com"));
            if(ticket.getParkingHours() == 0 && (Duration.between(ticket.getCheckIn(), Instant.now()).toHours() <= 1L) && ticket.getCheckOut() == null)
                emailService.sendMail(new EmailDTO());
        });
       }
}
