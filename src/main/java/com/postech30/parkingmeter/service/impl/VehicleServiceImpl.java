package com.postech30.parkingmeter.service.impl;

import com.postech30.parkingmeter.dto.VehicleDTO;
import com.postech30.parkingmeter.entity.Vehicle;
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
    @Override
    public VehicleDTO saveVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicleEntity = new Vehicle();
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

        Vehicle vehicle = vehicleRepository.getReferenceById(id);
        vehicle = mapTo(vehicleDTO, vehicle);
        vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public void checkIn(VehicleDTO vehicleDTO) {
        long id = vehicleDTO.getId();
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException(VEHICLE_NOT_FOUND);
        }


    }

    @Override
    @Transactional
    public void checkOut(VehicleDTO vehicleDTO) {

    }

    private Vehicle mapTo(VehicleDTO dto, Vehicle entity) {
        entity.setManufacturer(dto.getManufacturer());
        entity.setModel(dto.getModel());
        entity.setPlate(dto.getPlate());
        return entity;
    }
}
