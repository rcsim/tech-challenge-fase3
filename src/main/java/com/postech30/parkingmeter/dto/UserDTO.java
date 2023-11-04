package com.postech30.parkingmeter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.postech30.parkingmeter.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @JsonProperty
    @NotNull(message = "O nome é um campo de preenchimento obrigatório")
    private String name;

    @JsonProperty
    @NotNull(message = "O e-mail é um campo de preenchimento obrigatório")
    private String email;

    private String telephone;

    public UserDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        telephone = entity.getTelephone();
    }

}
