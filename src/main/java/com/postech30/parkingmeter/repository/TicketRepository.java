package com.postech30.parkingmeter.repository;

import com.postech30.parkingmeter.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCheckOutIsNullOrCheckOutGreaterThanEqual(Instant instant);

}
