package com.postech30.parkingmeter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_payment_method")
@EqualsAndHashCode
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String type;

    private String cardNumber;

    private String cardHolder;

    private String cvv;

    private String cardExpirationDate;
}
