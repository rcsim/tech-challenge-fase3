package com.postech30.parkingmeter.service;

import com.postech30.parkingmeter.dto.CardDTO;
import com.postech30.parkingmeter.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<UserDTO> searchUser(String searchUser, Pageable pageable);
    UserDTO findById(Long id);
    UserDTO createUser(UserDTO userDTO);
    void updateUser(Long id, UserDTO userDTO);
    void  deleteUser(Long id);
    List<CardDTO> findCardByUserId(Long id);
}
