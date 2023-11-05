package com.postech30.parkingmeter.service.impl;

import com.postech30.parkingmeter.dto.VehicleDTO;
import com.postech30.parkingmeter.entity.Vehicle;
import com.postech30.parkingmeter.exceptions.BadRequestException;
import com.postech30.parkingmeter.exceptions.ResourceNotFoundException;
import com.postech30.parkingmeter.repository.VehicleRepository;
import com.postech30.parkingmeter.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    private static final String VEHICLE_NOT_FOUND = "Veículo não encontrado";
    private static final String VEHICLE_DUPLICATED = "Placa do veículo já foi cadastrada";
    @Override
    public VehicleDTO saveVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicleEntity = new Vehicle();

        if(vehicleRepository.findByPlateIgnoreCaseContaining(vehicleDTO.getPlate()) != null){
           throw new BadRequestException(VEHICLE_DUPLICATED);
        }

        vehicleEntity = mapTo(vehicleDTO, vehicleEntity);
        return new VehicleDTO(vehicleRepository.save(vehicleEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleDTO> search(String text, Pageable pageable) {
        Page<Vehicle> page;
        if (Objects.equals(text, "")) {
            page = vehicleRepository.findAll(pageable);
        } else {
            page = vehicleRepository.findByManufacturerIgnoreCaseContainingOrModelIgnoreCaseContainingOrPlateIgnoreCaseContaining(text, text, text, pageable);
        }
        return page.map(VehicleDTO::new);
    }


    @Override
    @Transactional(readOnly = true)
    public VehicleDTO findById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(VEHICLE_NOT_FOUND));

        return new VehicleDTO(vehicle);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException(VEHICLE_NOT_FOUND);
        }

        vehicleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, VehicleDTO vehicleDTO) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException(VEHICLE_NOT_FOUND);
        }

        Vehicle vehicleByPlate = vehicleRepository.findByPlateIgnoreCaseContaining(vehicleDTO.getPlate());

        Vehicle vehicle = vehicleRepository.getReferenceById(id);
        vehicle = mapTo(vehicleDTO, vehicle);

        if(vehicle.getId() !=  vehicleByPlate.getId()){
            throw new BadRequestException(VEHICLE_DUPLICATED);
        }
        vehicleRepository.save(vehicle);
    }

    private Vehicle mapTo(VehicleDTO dto, Vehicle entity) {
        entity.setManufacturer(dto.getManufacturer());
        entity.setModel(dto.getModel());
        entity.setPlate(dto.getPlate());
        return entity;
    }

}
