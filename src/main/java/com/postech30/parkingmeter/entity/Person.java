package com.postech30.parkingmeter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_person")
public abstract class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String email;

    private String telephone;

    private String address;
}