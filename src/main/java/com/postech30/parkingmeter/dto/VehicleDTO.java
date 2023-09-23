package com.postech30.parkingmeter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.postech30.parkingmeter.entity.Vehicle;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    private Long id;

    @JsonProperty
    @NotBlank(message = "O fabricante é um campo de preenchimento obrigatório")
    private String manufacturer;

    @JsonProperty
    @NotBlank(message = "O modelo é um campo de preenchimento obrigatório")
    private String model;

    @JsonProperty
    @NotBlank(message = "A placa é um campo de preenchimento obrigatório")
    private String plate;

    public VehicleDTO(Vehicle entity){
        this.id = entity.getId();
        this.manufacturer = entity.getManufacturer();
        this.model = entity.getModel();
        this.plate = entity.getPlate();
    }
}
