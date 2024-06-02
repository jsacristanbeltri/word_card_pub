package com.jsacristan.word_card_back.repositories;

import com.jsacristan.word_card_back.models.Deck;
import com.jsacristan.word_card_back.models.Language;
import com.jsacristan.word_card_back.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Deck entities.
 */
@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {

    /**
     * Find decks by language and user.
     *
     * @param languageRequest The language of the deck.
     * @param userRequest     The user associated with the deck.
     * @return A list of decks matching the language and user.
     */
    List<Deck> findDeckByLanguageAndUser(Language languageRequest, User userRequest);

    /**
     * Find decks by user.
     *
     * @param user The user associated with the decks.
     * @return A list of decks belonging to the user.
     */
    List<Deck> findByUser(User user);

    /**
     * Find decks by language.
     *
     * @param languageRequest The language of the decks.
     * @return A list of decks matching the language.
     */
    @Query("SELECT d FROM Deck d WHERE d.language=?1")
    List<Deck> findDeckByLanguage(Language languageRequest);
}
