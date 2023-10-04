package com.postech30.parkingmeter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("PF")
public class PhysicalPerson extends Person {

    private String name;
    private String sexo;
    private String cpf;
}
