package com.postech30.parkingmeter.controller;

import com.postech30.parkingmeter.dto.CardDTO;
import com.postech30.parkingmeter.dto.UserDTO;
import com.postech30.parkingmeter.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "Busca todos os usuário pelo ID",
            description = "Busca todos os usuário na base de dados do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lista todos os usuários. Retorna uma lista vazia caso não existam usuários cadastrados")
    })
    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(@RequestParam(defaultValue = "") String searchUser, Pageable pageable) {
        Page<UserDTO> userPage = userService.searchUser(searchUser, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(userPage);
    }

    @Operation(summary = "Busca um usuário pelo ID",
            description = "Busca um usuário na base de dados do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lista um usuário. Retorna uma lista vazia caso não existam usuários cadastrados")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        var user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Busca os cartões cadastrados por um usuário",
            description = "Busca os cartões cadastrados por um usuário na base de dados do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lista de cartões do usuário"),
            @ApiResponse(responseCode = "400", description = "Request incorreto"),
            @ApiResponse(responseCode = "422", description = "Parâmetro não pode ser nulo")
    })
    @GetMapping(value = "{id}/cards")
    public ResponseEntity<List<CardDTO>> findApplianceDependent(@PathVariable Long id) {
        var user = userService.findCardByUserId(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Cria um usuário",
            description = "Cria um usuário na base de dados do sistema, o nome e o e-mail são de preenchimento obrigatório")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Request incorreto"),
            @ApiResponse(responseCode = "422", description = "Parâmetro não pode ser nulo")
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUserSave(@RequestBody @Valid UserDTO userDTO) {
        var userSave = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
    }

    @Operation(summary = "Altera um usuário",
            description = "Altera um usuário na base de dados do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "400", description = "Request incorreto"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "422", description = "Parâmetro não pode ser nulo")
    })
    @PutMapping(value = "{id}")
    public ResponseEntity<String> updatingUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário foi atualizado!!");
    }

    @Operation(summary = "Exclui um usuário",
            description = "Exclui um usuário na base de dados do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário excluido"),
            @ApiResponse(responseCode = "400", description = "Request incorreto"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "422", description = "Parâmetro não pode ser nulo")
    })
    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> deletingUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário foi Excluído!!");
    }
}
