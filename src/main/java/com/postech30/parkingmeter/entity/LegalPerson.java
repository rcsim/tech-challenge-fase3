package com.postech30.parkingmeter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DiscriminatorValue("PJ")
public class LegalPerson extends Person {

    private String razaoSocial;
    private String cnpj;
}
