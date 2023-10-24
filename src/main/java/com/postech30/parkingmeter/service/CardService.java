package com.postech30.parkingmeter.service;

import com.postech30.parkingmeter.dto.CardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardService {

    CardDTO createCard(CardDTO cardDTO);

    void deleteCard(Long id);

    void updateCard(Long id, CardDTO cardDTO);

    Page<CardDTO> searchCard(String search, Pageable pageable);

}
