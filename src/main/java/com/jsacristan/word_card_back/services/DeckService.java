package com.jsacristan.word_card_back.services;

import com.jsacristan.word_card_back.dtos.DeckOutDto;
import com.jsacristan.word_card_back.exceptions.BaseException;
import com.jsacristan.word_card_back.maps.DeckMapper;
import com.jsacristan.word_card_back.models.Deck;
import com.jsacristan.word_card_back.models.Language;
import com.jsacristan.word_card_back.models.User;
import com.jsacristan.word_card_back.repositories.DeckRepository;
import com.jsacristan.word_card_back.dtos.CreateDeckRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing decks.
 */
@Slf4j
@Service
public class DeckService {

    private final DeckRepository deckRepository;
    private final UserService userService;
    private final LanguageService languageService;
    private  DeckMapper deckMapper;

    @Autowired
    public DeckService(DeckRepository deckRepository, UserService userService,
                       LanguageService languageService) {
        this.deckRepository = deckRepository;
        this.userService = userService;
        this.languageService = languageService;
    }

    /**
     * Retrieves all decks.
     *
     * @return A list of all decks.
     * @throws BaseException If an error occurs while fetching decks.
     */
    public List<Deck> findAll() throws BaseException {
        try {
            log.info("Fetching all decks");
            return deckRepository.findAll();
        } catch (Exception e) {
            log.error("An error occurred while fetching decks: {}", e.getMessage());
            throw new BaseException("Error fetching decks", "DECK_FETCH_ERROR");
        }
    }

    /**
     * Retrieves all decks by username and language.
     *
     * @param usernameRequest The username.
     * @param languageRequest The language.
     * @return A list of decks that match the username and language.
     * @throws BaseException If an error occurs while fetching decks.
     */
    public List<Deck> findAllDecksByUsernameAndLanguage(String usernameRequest, String languageRequest) throws BaseException {
        try {
            log.info(String.format("Fetching all decks by username=%s and language=%s", usernameRequest, languageRequest));
            User user = userService.findByUsername(usernameRequest);
            Language language = languageService.findByLanguage(languageRequest);
            return deckRepository.findDeckByLanguageAndUser(language, user);
        } catch (Exception e) {
            log.error("An error occurred while fetching decks: {}", e.getMessage());
            throw new BaseException("Error fetching decks", "DECK_FETCH_ERROR");
        }
    }

    /**
     * Retrieves all decks by username.
     *
     * @param username The username.
     * @return A list of decks that match the username.
     * @throws BaseException If an error occurs while fetching decks.
     */
    public List<DeckOutDto> findAllDecksByUsername(String username) throws BaseException {
        try {
            log.info("Fetching all decks by username: " + username);
            User user = userService.findByUsername(username);
            List<Deck> decks = deckRepository.findByUser(user);
            List<DeckOutDto> responseDecks = new ArrayList<>();
            decks.forEach(deck -> responseDecks.add(deckMapper.deckToDeckOutDto(deck)));
            return responseDecks;
        } catch (Exception e) {
            log.error("An error occurred while fetching decks by username {}: {}", username, e.getMessage());
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    String.format("An error occurred while fetching decks by username %s: %s", username, e.getMessage()));
        }
    }

    /**
     * Retrieves a deck by its ID.
     *
     * @param id The ID of the deck to retrieve.
     * @return The deck with the specified ID.
     * @throws BaseException If the deck is not found or an error occurs while fetching the deck.
     */
    public Deck findById(Long id) throws BaseException {
        try {
            log.info("Fetching deck with id: {}", id);
            Optional<Deck> deck = deckRepository.findById(id);
            return deck.orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.toString(), String.format("Deck with id %s not found", id)));
        } catch (BaseException e) {
            log.warn("Deck not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while fetching deck with id {}: {}", id, e.getMessage());
            throw new BaseException("Error fetching deck", "DECK_FETCH_ERROR");
        }
    }

    /**
     * Saves a new deck.
     *
     * @param deckRequest The request DTO containing the deck details.
     * @return The saved deck.
     * @throws BaseException If an error occurs while saving the deck.
     */
    public Deck save(CreateDeckRequestDto deckRequest) throws BaseException {
        try {
            log.info("Saving deck: {}", deckRequest);
            User user = userService.findByUsername(deckRequest.getUsername());
            Language language = languageService.findByLanguage(deckRequest.getLanguage());
            return deckRepository.save(
                    Deck.builder()
                            .user(user)
                            .name(deckRequest.getName())
                            .description(deckRequest.getDescription())
                            .language(language)
                            .cards(deckRequest.getCards())
                            .build());
        } catch (Exception e) {
            log.error("An error occurred while saving deck: {}", e.getMessage());
            throw new BaseException("Error saving deck", "DECK_SAVE_ERROR");
        }
    }

    /**
     * Updates an existing deck.
     *
     * @param id           The ID of the deck to update.
     * @param deckDetails  The updated deck details.
     * @return The updated deck.
     * @throws BaseException If the deck is not found or an error occurs while updating the deck.
     */
    public Deck update(Long id, Deck deckDetails) throws BaseException {
        try {
            log.info("Updating deck with id: {}", id);
            Deck deck = deckRepository.findById(id)
                    .orElseThrow(() -> new BaseException("Deck not found", "DECK_NOT_FOUND"));
            deck.setName(deckDetails.getName());
            deck.setDescription(deckDetails.getDescription());
            deck.setUser(deckDetails.getUser());
            deck.setLanguage(deckDetails.getLanguage());
            deck.setCards(deckDetails.getCards());
            return deckRepository.save(deck);
        } catch (BaseException e) {
            log.warn("Deck not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while updating deck with id {}: {}", id, e.getMessage());
            throw new BaseException("Error updating deck", "DECK_UPDATE_ERROR");
        }
    }

    /**
     * Deletes a deck by its ID.
     *
     * @param id The ID of the deck to delete.
     * @throws BaseException If the deck is not found or an error occurs while deleting the deck.
     */
    public void deleteById(Long id) throws BaseException {
        try {
            log.info("Deleting deck with id: {}", id);
            Deck deck = deckRepository.findById(id)
                    .orElseThrow(() -> new BaseException("Deck not found", "DECK_NOT_FOUND"));
            deckRepository.deleteById(id);
        } catch (BaseException e) {
            log.warn("Deck not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while deleting deck with id {}: {}", id, e.getMessage());
            throw new BaseException("Error deleting deck", "DECK_DELETE_ERROR");
        }
    }
}
