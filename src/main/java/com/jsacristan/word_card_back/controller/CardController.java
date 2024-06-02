package com.jsacristan.word_card_back.controller;

import com.jsacristan.word_card_back.exceptions.BaseException;
import com.jsacristan.word_card_back.models.Card;
import com.jsacristan.word_card_back.dtos.CreateCardRequestDto;
import com.jsacristan.word_card_back.response.ResponseBuild;
import com.jsacristan.word_card_back.services.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing cards.
 * Provides endpoints for CRUD operations on cards.
 */
@Slf4j
@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;
    private final ResponseBuild responseBuilder;

    /**
     * Constructor for dependency injection.
     *
     * @param cardService the card service
     * @param responseBuilder the response builder
     */
    @Autowired
    public CardController(CardService cardService, ResponseBuild responseBuilder) {
        this.cardService = cardService;
        this.responseBuilder = responseBuilder;
    }

    /**
     * Retrieves all cards.
     *
     * @return an HTTP response with the status and the retrieved cards
     */
    @GetMapping
    public ResponseEntity<?> getAllCards() {
        try {
            log.info("Fetching all cards");
            List<Card> cards = cardService.findAll();
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Cards retrieved successfully", cards);
        } catch (BaseException e) {
            log.error("An error occurred while fetching all cards: {}", e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Retrieves a card by its ID.
     *
     * @param id the ID of the card to retrieve
     * @return an HTTP response with the status and the retrieved card
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCardById(@PathVariable Long id) {
        try {
            log.info("Fetching card with id: {}", id);
            Card card = cardService.findById(id);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Card found", card);
        } catch (BaseException e) {
            log.error("An error occurred while fetching card with id {}: {}", id, e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Creates a new card.
     *
     * @param cardRequest the DTO containing the request data to create a card
     * @return an HTTP response with the status and the created card
     */
    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody CreateCardRequestDto cardRequest) {
        try {
            log.info("Creating card: {}", cardRequest);
            Card savedCard = cardService.save(cardRequest);
            return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Card created successfully", savedCard);
        } catch (BaseException e) {
            log.error("An error occurred while creating card: {}", e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Updates an existing card.
     *
     * @param id the ID of the card to update
     * @param cardDetails the details of the updated card
     * @return an HTTP response with the status and the updated card
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCard(@PathVariable Long id, @RequestBody Card cardDetails) {
        try {
            log.info("Updating card with id: {}", id);
            Card updatedCard = cardService.update(id, cardDetails);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Card updated successfully", updatedCard);
        } catch (BaseException e) {
            log.error("An error occurred while updating card with id {}: {}", id, e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Deletes a card by its ID.
     *
     * @param id the ID of the card to delete
     * @return an HTTP response with the status of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id) {
        try {
            log.info("Deleting card with id: {}", id);
            cardService.deleteById(id);
            return responseBuilder.buildResponse(HttpStatus.NO_CONTENT.value(), "Card deleted successfully");
        } catch (BaseException e) {
            log.error("An error occurred while deleting card with id {}: {}", id, e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
