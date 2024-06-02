package com.jsacristan.word_card_back.controller;

import com.jsacristan.word_card_back.dtos.DeckOutDto;
import com.jsacristan.word_card_back.exceptions.BaseException;
import com.jsacristan.word_card_back.models.Deck;
import com.jsacristan.word_card_back.dtos.CreateDeckRequestDto;
import com.jsacristan.word_card_back.response.ResponseBuild;
import com.jsacristan.word_card_back.services.DeckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing decks.
 * Provides endpoints for CRUD operations on decks.
 */
@Slf4j
@RestController
@RequestMapping("/api/decks")
public class DeckController {

    private final DeckService deckService;
    private final ResponseBuild responseBuilder;

    /**
     * Constructor for dependency injection.
     *
     * @param deckService the deck service
     * @param responseBuilder the response builder
     */
    @Autowired
    public DeckController(DeckService deckService, ResponseBuild responseBuilder) {
        this.deckService = deckService;
        this.responseBuilder = responseBuilder;
    }

    /**
     * Retrieves all decks.
     *
     * @return an HTTP response with the status and the retrieved decks
     */
    @GetMapping
    public ResponseEntity<?> getAllDecks() {
        try {
            log.info("Fetching all decks");
            List<Deck> decks = deckService.findAll();
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Decks retrieved successfully", decks);
        } catch (BaseException e) {
            log.error("An error occurred while fetching all decks: {}", e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Retrieves all decks by username.
     *
     * @param username the username to filter decks by
     * @return an HTTP response with the status and the retrieved decks
     */
    @GetMapping("/username")
    public ResponseEntity<?> getAllDecksByUsername(@PathVariable String username) {
        try {
            log.info("Fetching all decks by username");
            List<DeckOutDto> decks = deckService.findAllDecksByUsername(username);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Decks retrieved successfully", decks);
        } catch (BaseException e) {
            log.error("An error occurred while fetching all decks: {}", e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Retrieves all decks by username and language.
     *
     * @param usernameRequest the username to filter decks by
     * @param languageRequest the language to filter decks by
     * @return an HTTP response with the status and the retrieved decks
     */
    @GetMapping("/username/{usernameRequest}/language/{languageRequest}")
    public ResponseEntity<?> getAllDecksByUsernameAndLanguage(
            @PathVariable String usernameRequest,
            @PathVariable String languageRequest) {
        try {
            log.info("Fetching all decks by username and language");
            List<Deck> decks = deckService.findAllDecksByUsernameAndLanguage(usernameRequest, languageRequest);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Decks retrieved successfully", decks);
        } catch (BaseException e) {
            log.error("An error occurred while fetching all decks by username {} and language {}: {}",
                    usernameRequest, languageRequest, e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Retrieves a deck by its ID.
     *
     * @param id the ID of the deck to retrieve
     * @return an HTTP response with the status and the retrieved deck
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getDeckById(@PathVariable Long id) {
        try {
            log.info("Fetching deck with id: {}", id);
            Deck deck = deckService.findById(id);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Deck found", deck);
        } catch (BaseException e) {
            log.error("An error occurred while fetching deck with id {}: {}", id, e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Creates a new deck.
     *
     * @param deckRequest the DTO containing the request data to create a deck
     * @return an HTTP response with the status and the created deck
     */
    @PostMapping
    public ResponseEntity<?> createDeck(@RequestBody CreateDeckRequestDto deckRequest) {
        try {
            log.info("Creating deck: {}", deckRequest);
            Deck savedDeck = deckService.save(deckRequest);
            return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Deck created successfully", savedDeck);
        } catch (BaseException e) {
            log.error("An error occurred while creating deck: {}", e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Updates an existing deck.
     *
     * @param id the ID of the deck to update
     * @param deckDetails the details of the updated deck
     * @return an HTTP response with the status and the updated deck
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeck(@PathVariable Long id, @RequestBody Deck deckDetails) {
        try {
            log.info("Updating deck with id: {}", id);
            Deck updatedDeck = deckService.update(id, deckDetails);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Deck updated successfully", updatedDeck);
        } catch (BaseException e) {
            log.error("An error occurred while updating deck with id {}: {}", id, e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Deletes a deck by its ID.
     *
     * @param id the ID of the deck to delete
     * @return an HTTP response with the status of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeck(@PathVariable Long id) {
        try {
            log.info("Deleting deck with id: {}", id);
            deckService.deleteById(id);
            return responseBuilder.buildResponse(HttpStatus.NO_CONTENT.value(), "Deck deleted successfully");
        } catch (BaseException e) {
            log.error("An error occurred while deleting deck with id {}: {}", id, e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
