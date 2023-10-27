package com.postech30.parkingmeter.controller;

import com.postech30.parkingmeter.dto.CardDTO;
import com.postech30.parkingmeter.service.CardService;
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

    @PostMapping
    public ResponseEntity<CardDTO> savePaymentMethod(@RequestBody @Valid CardDTO cardDTO) {
        var paymentMethodSave = cardService.createCard(cardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentMethodSave);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<String> updatingPaymentMethod(@PathVariable Long id, @RequestBody @Valid CardDTO cardDTO) {
        cardService.updateCard(id, cardDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Método de pagamento foi atualizado!!");
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> deletingPaymentMethod(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.status(HttpStatus.OK).body("Método de pagamento foi excluído!!");
    }

}
