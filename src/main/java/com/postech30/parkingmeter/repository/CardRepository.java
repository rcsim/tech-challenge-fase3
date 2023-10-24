package com.postech30.parkingmeter.repository;

import com.postech30.parkingmeter.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByUserId(Long id);

    Page<Card> findByCardHolderIgnoreCaseContainingOrCardExpirationDateIgnoreCaseContainingOrCardNumberIgnoreCaseContaining(String cardHolder, LocalDate cardExpirationDate, String cardNumber, Pageable pageable);
}
