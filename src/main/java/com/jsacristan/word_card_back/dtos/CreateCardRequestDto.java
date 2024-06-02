package com.jsacristan.word_card_back.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Data Transfer Object for creating a card.
 * This class is used to encapsulate the data needed to create a card.
 */
public class CreateCardRequestDto implements Serializable {

    /**
     * The first name of the card.
     */
    @JsonProperty("name1")
    @NotNull(message = "name1 is mandatory")
    @NotBlank(message = "name1 is mandatory")
    private String name1;

    /**
     * The second name of the card.
     */
    @JsonProperty("name2")
    @NotNull(message = "name2 is mandatory")
    @NotBlank(message = "name2 is mandatory")
    private String name2;

    /**
     * The ID of the deck to which the card belongs.
     */
    @JsonProperty("idDeck")
    @NotNull(message = "idDeck is mandatory")
    private Long idDeck;

    /**
     * Gets the first name of the card.
     *
     * @return the first name of the card
     */
    public String getName1() {
        return name1;
    }

    /**
     * Gets the second name of the card.
     *
     * @return the second name of the card
     */
    public String getName2() {
        return name2;
    }

    /**
     * Gets the ID of the deck to which the card belongs.
     *
     * @return the deck ID
     */
    public Long getIdDeck() {
        return idDeck;
    }
}
