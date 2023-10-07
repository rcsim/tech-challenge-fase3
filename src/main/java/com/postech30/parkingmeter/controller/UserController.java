package com.postech30.parkingmeter.controller;

import com.postech30.parkingmeter.dto.UserDTO;
import com.postech30.parkingmeter.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@Validated
@Transactional
public class UserController {
    @Autowired
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(@RequestParam(defaultValue = "") String searchUser, Pageable pageable) {
        Page<UserDTO> userPage = userService.searchUser(searchUser, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(userPage);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        var user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
    @PostMapping
    public ResponseEntity<UserDTO> createUserSave(@RequestBody @Valid UserDTO userDTO) {
        var userSave = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
    }
    @PutMapping(value = "{id}")
    public ResponseEntity<String> updatingUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário foi atualizado!!");
    }
    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> deletingUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário foi Excluído!!");
    }
}
