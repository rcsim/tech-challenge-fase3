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
@Table(name = "tb_users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String email;

    private String telephone;

    private String address;
}
