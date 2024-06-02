package com.jsacristan.word_card_back.dtos;

import com.jsacristan.word_card_back.models.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for outputting deck information.
 * This class is used to encapsulate the data for a deck.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeckOutDto {

    /**
     * The unique identifier of the deck.
     */
    private long id;

    /**
     * The name of the deck.
     */
    private String name;

    /**
     * The description of the deck.
     */
    private String description;

    /**
     * The language of the deck.
     */
    private String language;

    /**
     * The username of the deck creator.
     */
    private String username;

    /**
     * The number of cards in the deck.
     */
    private Integer numberOfCards;

    /**
     * The list of cards in the deck.
     */
    private List<Card> cards;
}
