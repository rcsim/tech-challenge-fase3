package com.postech30.parkingmeter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_pessoa_fisica")
public class PhysicalPerson extends Person implements Serializable {

    private String cpf;
}
