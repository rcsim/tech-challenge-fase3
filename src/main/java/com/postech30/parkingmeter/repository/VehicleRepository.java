package com.postech30.parkingmeter.repository;

import com.postech30.parkingmeter.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Page<Vehicle> findByManufacturerIgnoreCaseContainingOrModelIgnoreCaseContainingOrPlateIgnoreCaseContaining(String manufacturer, String model, String plate, Pageable pageable);

    Vehicle findByPlateIgnoreCaseContaining(String plate);



}
