package com.postech30.parkingmeter.service.impl;

import com.postech30.parkingmeter.dto.UserDTO;
import com.postech30.parkingmeter.entity.User;
import com.postech30.parkingmeter.repository.UserRepository;
import com.postech30.parkingmeter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDTO findById(Long id) {
        User entity = userRepository.findById(id).get();
        return new UserDTO(entity);
    }

}
