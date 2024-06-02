package com.jsacristan.word_card_back.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Data Transfer Object for requesting deck information.
 * This class is used to encapsulate the data needed to request a deck.
 */
@Data
@RequiredArgsConstructor
public class DeckRequestDto {

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
}
