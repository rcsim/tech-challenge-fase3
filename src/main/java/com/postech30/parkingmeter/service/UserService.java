package com.postech30.parkingmeter.service;

import com.postech30.parkingmeter.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDTO> searchUser(String searchUser, Pageable pageable);
    UserDTO findById(Long id);

}
