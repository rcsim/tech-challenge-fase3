package com.postech30.parkingmeter.service;

import com.postech30.parkingmeter.dto.VehicleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleService {
    VehicleDTO saveVehicle(VehicleDTO VehicleDTO);

    Page<VehicleDTO> search(String search, Pageable pageable);

    VehicleDTO findById(Long id);

    void delete(Long id);

    void update(Long id, VehicleDTO VehicleDTO);

    void checkIn(VehicleDTO vehicleDTO);

    void checkOut(VehicleDTO vehicleDTO);
}
