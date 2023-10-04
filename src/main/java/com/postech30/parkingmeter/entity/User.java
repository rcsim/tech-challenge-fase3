package com.postech30.parkingmeter.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name = "tb_users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "personType", discriminatorType = DiscriminatorType.STRING)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String telephone;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

}
