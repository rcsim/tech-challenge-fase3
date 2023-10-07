package com.postech30.parkingmeter.repository;

import com.postech30.parkingmeter.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT p.hourPrice FROM Price p ORDER BY p.id DESC")
    Double findLastHourPrice();

}
