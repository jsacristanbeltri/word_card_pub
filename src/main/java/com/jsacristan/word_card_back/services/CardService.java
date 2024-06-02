package com.jsacristan.word_card_back.services;

import com.jsacristan.word_card_back.exceptions.BaseException;
import com.jsacristan.word_card_back.models.Card;
import com.jsacristan.word_card_back.models.Deck;
import com.jsacristan.word_card_back.repositories.CardRepository;
import com.jsacristan.word_card_back.dtos.CreateCardRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing cards.
 */
@Slf4j
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final DeckService deckService;

    @Autowired
    public CardService(CardRepository cardRepository, DeckService deckService) {
        this.cardRepository = cardRepository;
        this.deckService = deckService;
    }

    /**
     * Retrieves all cards.
     *
     * @return A list of all cards.
     * @throws BaseException If an error occurs while fetching cards.
     */
    public List<Card> findAll() throws BaseException {
        try {
            log.info("Fetching all cards");
            return cardRepository.findAll();
        } catch (Exception e) {
            log.error("An error occurred while fetching cards: {}", e.getMessage());
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    "An error occurred while fetching cards, " + e.getMessage());
        }
    }

    /**
     * Retrieves a card by its ID.
     *
     * @param id The ID of the card to retrieve.
     * @return The card with the specified ID.
     * @throws BaseException If the card is not found or an error occurs while fetching the card.
     */
    public Card findById(Long id) throws BaseException {
        try {
            log.info("Fetching card with id: {}", id);
            Optional<Card> card = cardRepository.findById(id);
            return card.orElseThrow(() -> new BaseException("Card not found", "CARD_NOT_FOUND"));
        } catch (BaseException e) {
            log.warn("Card not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while fetching card with id {}: {}", id, e.getMessage());
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    String.format("An error occurred while fetching card with id %s: %s", id, e.getMessage()));
        }
    }

    /**
     * Saves a new card.
     *
     * @param cardRequest The request DTO containing the card details.
     * @return The saved card.
     * @throws BaseException If an error occurs while saving the card.
     */
    public Card save(CreateCardRequestDto cardRequest) throws BaseException {
        try {
            log.info("Saving card: {}", cardRequest);
            Deck deck = deckService.findById(cardRequest.getIdDeck());
            return cardRepository.save(
                    new Card(
                            cardRequest.getName1(),
                            cardRequest.getName2(),
                            true,
                            null,
                            0,
                            deck));
        } catch (Exception e) {
            log.error("An error occurred while saving card: {}", e.getMessage());
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    String.format("An error occurred while saving card: %s", e.getMessage()));
        }
    }

    /**
     * Updates an existing card.
     *
     * @param id           The ID of the card to update.
     * @param cardDetails  The updated card details.
     * @return The updated card.
     * @throws BaseException If the card is not found or an error occurs while updating the card.
     */
    public Card update(Long id, Card cardDetails) throws BaseException {
        try {
            log.info("Updating card with id: {}", id);
            Card card = cardRepository.findById(id)
                    .orElseThrow(() -> new BaseException("Card not found", "CARD_NOT_FOUND"));
            card.setName1(cardDetails.getName1());
            card.setName2(cardDetails.getName2());
            card.setEnable(cardDetails.isEnable());
            card.setLastTry(cardDetails.getLastTry());
            card.setPeriodDaysReminder(cardDetails.getPeriodDaysReminder());
            card.setDeck(cardDetails.getDeck());
            return cardRepository.save(card);
        } catch (BaseException e) {
            log.warn("Card not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while updating card with id {}: {}", id, e.getMessage());
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    String.format("An error occurred while updating card with id %s: %s", id, e.getMessage()));
        }
    }

    /**
     * Deletes a card by its ID.
     *
     * @param id The ID of the card to delete.
     * @throws BaseException If the card is not found or an error occurs while deleting the card.
     */
    public void deleteById(Long id) throws BaseException {
        try {
            log.info("Deleting card with id: {}", id);
            Card card = cardRepository.findById(id)
                    .orElseThrow(() -> new BaseException("Card not found", "CARD_NOT_FOUND"));
            cardRepository.deleteById(id);
        } catch (BaseException e) {
            log.warn("Card not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while deleting card with id {}: {}", id, e.getMessage());
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    String.format("An error occurred while deleting card with id %s: %s", id, e.getMessage()));
        }
    }
}
