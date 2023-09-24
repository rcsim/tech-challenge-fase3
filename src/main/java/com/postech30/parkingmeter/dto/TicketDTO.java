package com.postech30.parkingmeter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.postech30.parkingmeter.entity.Ticket;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "O veículo é um campo de preenchimento obrigatório")
    private Long vehicleId;

    private Instant checkIn;

    private Instant checkOut;

    @JsonProperty("parkingHours")
    @Min(value = 0, message = "Esse campo deve ser um número inteiro não negativo")
    private Long parkingHours = 0L;

    public TicketDTO(Ticket entity){
        this.id = entity.getId();
        this.vehicleId = entity.getVehicle().getId();
        this.checkIn = entity.getCheckIn();
        this.checkOut = entity.getCheckOut();
    }

    public TicketDTO(Ticket entity, Long parkingHours){
        this.id = entity.getId();
        this.vehicleId = entity.getVehicle().getId();
        this.checkIn = entity.getCheckIn();
        this.checkOut = entity.getCheckOut();
        this.parkingHours = parkingHours;
    }
}
