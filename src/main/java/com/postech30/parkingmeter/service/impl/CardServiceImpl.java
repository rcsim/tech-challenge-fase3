package com.postech30.parkingmeter.service.impl;

import com.postech30.parkingmeter.dto.CardDTO;
import com.postech30.parkingmeter.entity.Card;
import com.postech30.parkingmeter.exceptions.ResourceNotFoundException;
import com.postech30.parkingmeter.repository.CardRepository;
import com.postech30.parkingmeter.service.CardService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@Service
public class CardServiceImpl implements CardService {

    private static final String CARD_NOT_FOUND = "Cartão não encontrado";
    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional
    public @Valid CardDTO createCard(CardDTO cardDTO) {
        Card card = new Card();
        mapTo(cardDTO, card);
        return new CardDTO(cardRepository.save(card));
    }

    @Override
    @Transactional
    public void updateCard(Long id, CardDTO cardDTO) {
        if (!cardRepository.existsById(id)) {
            throw new ResourceNotFoundException(CARD_NOT_FOUND);
        }

        Card card = cardRepository.getReferenceById(id);
        mapTo(cardDTO, card);
        cardRepository.save(card);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteCard(Long id) {
        if (!cardRepository.existsById(id)) {
            throw new ResourceNotFoundException(CARD_NOT_FOUND);
        }
        cardRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CardDTO> searchCard(String text, Pageable pageable) {
        Page<Card> page;
        if (Objects.equals(text, "")) {
            page = cardRepository.findAll(pageable);
        } else {
            page = cardRepository.findByCardHolderIgnoreCaseContainingOrCardExpirationDateIgnoreCaseContainingOrCardNumberIgnoreCaseContaining(text, LocalDate.parse(text), text, pageable);
        }
        return page.map(CardDTO::new);
    }

    private Card mapTo(CardDTO dto, Card entity) {
        entity.setType(dto.getType());
        entity.setUserId(dto.getUserId());
        entity.setCardHolder(dto.getCardHolder());
        entity.setCardNumber(dto.getCardNumber());
        entity.setCardExpirationDate(dto.getCardExpirationDate());
        entity.setCvv(dto.getCvv());
        return entity;
    }
}
