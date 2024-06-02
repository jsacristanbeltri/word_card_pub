package com.jsacristan.word_card_back.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jsacristan.word_card_back.models.Card;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Data Transfer Object for creating a deck.
 * This class is used to encapsulate the data needed to create a deck.
 */
@Data
public class CreateDeckRequestDto implements Serializable {

    /**
     * The name of the deck.
     */
    @NotNull(message = "name is mandatory, it can't be null")
    @NotBlank(message = "name is mandatory, it can't be empty")
    @JsonProperty("name")
    private String name;

    /**
     * The description of the deck.
     */
    @JsonProperty("description")
    private String description;

    /**
     * The language of the deck.
     */
    @JsonProperty("language")
    @NotNull(message = "language is mandatory, it can't be null")
    @NotBlank(message = "language is mandatory, it can't be empty")
    private String language;

    /**
     * The username of the deck creator.
     */
    private String username;

    /**
     * The list of cards in the deck.
     */
    private List<Card> cards;
}
