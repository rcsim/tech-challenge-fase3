package com.postech30.parkingmeter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.postech30.parkingmeter.entity.Card;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {

    private Long id;

    @JsonProperty
    @NotNull(message = "O usuário é um campo de preenchimento obrigatório")
    private Long userId;

    @JsonProperty
    @NotNull(message = "O tipo é um campo de preenchimento obrigatório")
    private String type;

    @JsonProperty
    @NotNull(message = "O número do cartão é um campo de preenchimento obrigatório")
    private String cardNumber;

    @JsonProperty
    @NotNull(message = "O nome é um campo de preenchimento obrigatório")
    private String cardHolder;

    @JsonProperty
    @NotNull(message = "O cvv é um campo de preenchimento obrigatório")
    private String cvv;

    @JsonProperty
    @NotNull(message = "A data de vencimento é obrigatória. Padrão MM/yyyy")
    private String cardExpirationDate;

    public CardDTO(Card entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.type = entity.getType();
        this.cardNumber = entity.getCardNumber();
        this.cardHolder = entity.getCardHolder();
        this.cvv = entity.getCvv();
        this.cardExpirationDate = entity.getCardExpirationDate();
    }
}
