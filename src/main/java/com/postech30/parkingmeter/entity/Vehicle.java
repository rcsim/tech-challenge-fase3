package com.postech30.parkingmeter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_vehicles")
@EqualsAndHashCode
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String manufacturer;

    private String model;

    private String plate;

    @OneToMany(mappedBy = "vehicle")
    private List<Ticket> tickets = new ArrayList<>();
}
