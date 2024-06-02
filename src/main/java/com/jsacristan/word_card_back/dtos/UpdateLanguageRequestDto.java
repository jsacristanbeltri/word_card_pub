package com.jsacristan.word_card_back.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Data Transfer Object for updating a language.
 * This class is used to encapsulate the data needed to update a language.
 */
@Data
@NoArgsConstructor
public class UpdateLanguageRequestDto implements Serializable {

    /**
     * The ID of the language to update.
     */
    Long id;

    /**
     * The updated language name.
     */
    String language;

    /**
     * The updated image associated with the language.
     */
    String image;
}
