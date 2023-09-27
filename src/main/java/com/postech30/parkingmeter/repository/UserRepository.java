package com.postech30.parkingmeter.repository;

import com.postech30.parkingmeter.entity.User;
import com.postech30.parkingmeter.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

}
