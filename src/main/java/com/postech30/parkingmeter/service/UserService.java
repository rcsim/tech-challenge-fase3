package com.postech30.parkingmeter.service;

import com.postech30.parkingmeter.dto.UserDTO;
import org.springframework.stereotype.Service;

public interface UserService {

    UserDTO findById(Long id);
}
