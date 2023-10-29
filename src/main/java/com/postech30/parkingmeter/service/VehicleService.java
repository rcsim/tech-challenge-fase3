package com.postech30.parkingmeter.service;

import com.postech30.parkingmeter.dto.UserDTO;
import com.postech30.parkingmeter.dto.VehicleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleService {
    VehicleDTO saveVehicle(VehicleDTO VehicleDTO);

    Page<VehicleDTO> search(String search, Pageable pageable);

    VehicleDTO findById(Long id);

    void delete(Long id);

    void update(Long id, VehicleDTO VehicleDTO);


}
