package com.postech30.parkingmeter.repository;

import com.postech30.parkingmeter.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByNameIgnoreCaseContainingOrEmailIgnoreCaseContainingOrTelephoneIgnoreCaseContaining(
            String name,
            String email,
            String telephone,
            Pageable pageable
    );
}
