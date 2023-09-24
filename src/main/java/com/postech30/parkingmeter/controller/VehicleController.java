package com.postech30.parkingmeter.controller;

import com.postech30.parkingmeter.dto.VehicleDTO;
import com.postech30.parkingmeter.service.VehicleService;
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

@RestController
@RequestMapping(value = "/vehicle")
@Validated
@Transactional
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Operation(summary = "Cadastro de Veículos",
            description = "Adiciona um Veículo na base de dados do sistema, todos os parâmetros são obrigatórios")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Veículo adicionado"),
            @ApiResponse(responseCode = "400", description = "Request incorreto"),
            @ApiResponse(responseCode = "422", description = "Parâmetro não pode ser nulo")
    })
    @PostMapping
    public ResponseEntity<VehicleDTO> addVehicle(@RequestBody @Valid VehicleDTO vehicleDTO){

        var vehicleSave = vehicleService.saveVehicle(vehicleDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleSave);

    }

    @Operation(summary = "Busca de Veículos",
            description = "Busca todos os Veículos na base de dados do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de Veículos cadastrados no sistema. Retorna uma lista vazia caso não exista Veículos cadastrados"),
    })
    @GetMapping
    public  ResponseEntity<Page<VehicleDTO>> getVehicles(
            @RequestParam(defaultValue = "") String search, Pageable pageable)  {

        Page<VehicleDTO> page = vehicleService.search(search, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Operation(summary = "Busca Veículo por Id",
            description = "Busca de Veículo por Id na base de dados do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna o Veículo correspondente ao id fornecido e cadastrado no sistema."),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @GetMapping(value = "{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {

        VehicleDTO dto = vehicleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(summary = "Remove Veículo",
            description = "Remove um Veículo na base de dados do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veículo deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> removeVehicle(@PathVariable Long id) {

        vehicleService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Veículo deletado com sucesso!");
    }

    @Operation(summary = "Atualiza cadastro de Veículos",
            description = "Atualiza o cadastro de um Veículo na base de dados do sistema, todos os parâmetros são obrigatórios")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veículo atualizado"),
            @ApiResponse(responseCode = "400", description = "Request incorreto"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
            @ApiResponse(responseCode = "422", description = "Parâmetro não pode ser nulo")
    })
    @PutMapping(value = "{id}")
    public ResponseEntity<String> updateVehicle(@PathVariable Long id, @RequestBody @Valid VehicleDTO vehicleDTO) {

        vehicleService.update(id, vehicleDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Veículo atualizado com sucesso!");
    }
}
