package com.postech30.parkingmeter.service.impl;

import com.postech30.parkingmeter.dto.EmailDTO;
import com.postech30.parkingmeter.dto.TicketDTO;
import com.postech30.parkingmeter.dto.UserDTO;
import com.postech30.parkingmeter.service.EmailService;
import com.postech30.parkingmeter.service.ScheduleService;
import com.postech30.parkingmeter.service.TicketService;
import com.postech30.parkingmeter.service.UserService;
import com.postech30.parkingmeter.util.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class ScheduleServiceImpl  implements ScheduleService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;


    @Scheduled(initialDelay = 1000,fixedDelay =30 * 60 * 1000 )
    @Override
    public void sendNotification() {

        List<TicketDTO> openTickets = ticketService.searchOpenTickets();

        openTickets.forEach(ticket -> {
            UserDTO user = userService.findById(ticket.getUserId());

            if (ticket.getParkingHours() <= 1) {
                    emailService.sendMail(new EmailDTO(user.getEmail(), user.getEmail()));
            }

            if (ticket.getParkingHours() == 0 && ticket.getCheckOut() == null &&
                    Duration.between(ticket.getCheckIn(), Instant.now()).toHours() <= 1L) {
                    emailService.sendMail(new EmailDTO(user.getEmail(), user.getEmail()));
            }
        });
    }
}
