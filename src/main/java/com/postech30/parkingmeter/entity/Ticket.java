package com.postech30.parkingmeter.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_tickets")
@EqualsAndHashCode
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    private Instant checkIn;

    private Instant checkOut;

    private String status;

    private String pixCode;

    private int paymentType;

    private Double price;
}
