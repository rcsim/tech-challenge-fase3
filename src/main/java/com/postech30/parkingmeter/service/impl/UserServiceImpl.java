package com.postech30.parkingmeter.service.impl;

import com.postech30.parkingmeter.dto.UserDTO;
import com.postech30.parkingmeter.entity.User;
import com.postech30.parkingmeter.repository.UserRepository;
import com.postech30.parkingmeter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> searchUser(String text, Pageable pageable) {

        Page<User> pageUser;
        if(Objects.equals(text, " ")) {
            pageUser = userRepository.findAll(pageable);
        } else {
            pageUser = userRepository
                    .findByNameIgnoreCaseContainingOrEmailIgnoreCaseContainingOrTelephoneIgnoreCaseContaining(
                            text,
                            text,
                            text,
                            pageable
                    );
        }
        return pageUser.map(UserDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User userFind = userRepository.findById(id).get();
        return new UserDTO(userFind);
    }
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User userCreate = new User();
        userCreate = mapTo(userDTO, userCreate);
        return new UserDTO(userRepository.save(userCreate));
    }
    @Override
    @Transactional
    public void updateUser(Long id, UserDTO userDTO){
        User userUpdate = userRepository.getReferenceById(id);
        userUpdate = mapTo(userDTO, userUpdate);
        userRepository.save(userUpdate);
    }
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    private User mapTo(UserDTO userDTO, User user) {
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setTelephone(user.getTelephone());
        return user;
    }
}
