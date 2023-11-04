package com.postech30.parkingmeter.controller;

import com.postech30.parkingmeter.dto.CardDTO;
import com.postech30.parkingmeter.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/card")
@Validated
@Transactional
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @Operation(summary = "Cadastro de cartão",
            description = "Adiciona um cartão na base de dados do sistema, todos os campos são de preenchimento obrigatório")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cartão adicionado"),
            @ApiResponse(responseCode = "400", description = "Request incorreto"),
            @ApiResponse(responseCode = "422", description = "Parâmetro não pode ser nulo")
    })
    @PostMapping
    public ResponseEntity<CardDTO> savePaymentMethod(@RequestBody @Valid CardDTO cardDTO) {
        var paymentMethodSave = cardService.createCard(cardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentMethodSave);
    }

    @Operation(summary = "Atualização de dados do cartão",
            description = "Atualiza um cartão na base de dados do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cartão atualizado"),
            @ApiResponse(responseCode = "400", description = "Request incorreto"),
            @ApiResponse(responseCode = "404", description = "Cartão não encontrado"),
            @ApiResponse(responseCode = "422", description = "Parâmetro não pode ser nulo")
    })
    @PutMapping(value = "{id}")
    public ResponseEntity<String> updatingPaymentMethod(@PathVariable Long id, @RequestBody @Valid CardDTO cardDTO) {
        cardService.updateCard(id, cardDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Os dados do cartão foram atualizados!!");
    }

    @Operation(summary = "Exclui um cartão",
            description = "Exclui um cartão na base de dados do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cartão excluido"),
            @ApiResponse(responseCode = "400", description = "Request incorreto"),
            @ApiResponse(responseCode = "404", description = "Cartão não encontrado"),
            @ApiResponse(responseCode = "422", description = "Parâmetro não pode ser nulo")
    })
    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> deletingPaymentMethod(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.status(HttpStatus.OK).body("O cartão foi excluído!!");
    }

}
