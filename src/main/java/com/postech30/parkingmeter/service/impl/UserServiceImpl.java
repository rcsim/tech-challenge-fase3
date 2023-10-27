package com.postech30.parkingmeter.service.impl;

import com.postech30.parkingmeter.dto.CardDTO;
import com.postech30.parkingmeter.dto.UserDTO;
import com.postech30.parkingmeter.entity.Card;
import com.postech30.parkingmeter.entity.User;
import com.postech30.parkingmeter.repository.CardRepository;
import com.postech30.parkingmeter.repository.UserRepository;
import com.postech30.parkingmeter.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    final CardRepository cardRepository;

    public UserServiceImpl(UserRepository userRepository, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
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

    @Override
    public List<CardDTO> findCardByUserId(Long id) {
        User user = userRepository.getReferenceById(id);
        List<Card> userCard = cardRepository.findByUserId(user.getId());
        return userCard.stream().map(CardDTO::new).toList();
    }

    private User mapTo(UserDTO userDTO, User user) {
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setTelephone(userDTO.getTelephone());
        return user;
    }
}
