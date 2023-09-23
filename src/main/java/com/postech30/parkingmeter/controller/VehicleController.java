package com.postech30.parkingmeter.controller;

import com.postech30.parkingmeter.dto.VehicleDTO;
import com.postech30.parkingmeter.service.VehicleService;
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

    @PostMapping
    public ResponseEntity<VehicleDTO> addVehicle(@RequestBody @Valid VehicleDTO vehicleDTO){

        var vehicleSave = vehicleService.saveVehicle(vehicleDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleSave);

    }

    @GetMapping
    public  ResponseEntity<Page<VehicleDTO>> getVehicles(
            @RequestParam(defaultValue = "") String search, Pageable pageable)  {

        Page<VehicleDTO> page = vehicleService.search(search, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {

        VehicleDTO dto = vehicleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> removeVehicle(@PathVariable Long id) {

        vehicleService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Veículo deletado com sucesso!");
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<String> updateVehicle(@PathVariable Long id, @RequestBody @Valid VehicleDTO vehicleDTO) {

        vehicleService.update(id, vehicleDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Veículo atualizado com sucesso!");
    }
}
