package com.postech30.parkingmeter.dto;

import com.postech30.parkingmeter.entity.Person;
import com.postech30.parkingmeter.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;

    public UserDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
    }

}
