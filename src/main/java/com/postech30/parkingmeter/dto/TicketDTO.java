package com.postech30.parkingmeter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.postech30.parkingmeter.entity.Ticket;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private Long id;

    @JsonProperty
    @NotBlank(message = "O veículo é um campo de preenchimento obrigatório")
    private Long vehicleId;

    private Instant checkIn;

    private Instant checkOut;

    public TicketDTO(Ticket entity){
        this.id = entity.getId();
        this.vehicleId = entity.getVehicleId();
        this.checkIn = entity.getCheckIn();
        this.checkOut = entity.getCheckOut();
    }
}
