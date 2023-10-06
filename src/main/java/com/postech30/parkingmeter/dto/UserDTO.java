package com.postech30.parkingmeter.dto;

import com.postech30.parkingmeter.entity.Person;
import com.postech30.parkingmeter.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String telephone;

    public UserDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        telephone = entity.getTelephone();
    }

}
