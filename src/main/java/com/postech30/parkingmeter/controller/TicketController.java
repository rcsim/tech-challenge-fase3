package com.postech30.parkingmeter.controller;

import com.postech30.parkingmeter.dto.TicketDTO;
import com.postech30.parkingmeter.service.TicketService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping(value = "/ticket")
@Validated
@Transactional
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Operation(summary = "Cadastro de Tickets",
            description = "Adiciona um Ticket na base de dados do sistema, apenas o id do veículo é de preenchimento obrigatório")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ticket adicionado"),
            @ApiResponse(responseCode = "400", description = "Request incorreto"),
            @ApiResponse(responseCode = "422", description = "Parâmetro não pode ser nulo")
    })
    @PostMapping("/checkin")
    public ResponseEntity<TicketDTO> checkIn(@RequestBody @Valid TicketDTO ticketDTO) {
        ticketDTO = ticketService.checkIn(ticketDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(ticketDTO);
    }

    @Operation(summary = "Busca de Tickets abertos",
            description = "Busca todos os Tickets sem checkout na base de dados do sistema e calcula a quantidade de horas que eles estão estacionados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de Tickets cadastrados no sistema. Retorna uma lista vazia caso não existam Tickets cadastrados"),
    })
    @GetMapping(value = "/open")
    public  ResponseEntity<List<TicketDTO>> getOpenTickets()  {
        List<TicketDTO> list = ticketService.searchOpenTickets();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @Operation(summary = "Conclusão de Tickets",
            description = "Atualiza o campo checkOut de um Ticket na base de dados do sistema, apenas o id do veículo é passado pelo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ticket fechado"),
            @ApiResponse(responseCode = "400", description = "Request incorreto"),
            @ApiResponse(responseCode = "404", description = "Ticket não encontrado"),
            @ApiResponse(responseCode = "422", description = "Parâmetro não pode ser nulo")
    })
    @PostMapping("/checkout")
    public ResponseEntity<TicketDTO> checkOut(@RequestParam Long id) {
        TicketDTO ticketDTO = ticketService.checkOut(id);
        return  ResponseEntity.ok(ticketDTO);
    }
}
