package com.postech30.parkingmeter.repository;

import com.postech30.parkingmeter.entity.User;
import com.postech30.parkingmeter.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByNameIgnoreCaseContainingOrEmailIgnoreCaseContainingOrTelephoneIgnoreCaseContaining(
            String name,
            String email,
            String telephone,
            Pageable pageable
    );

    @Query("SELECT u FROM User u INNER JOIN u.vehicles v WHERE v.id = :vehicleId")
    List<User> findUserByVehicleId(@Param("vehicleId") Long vehicleId);
}
