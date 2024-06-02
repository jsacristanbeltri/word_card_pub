package com.jsacristan.word_card_back.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Data Transfer Object for creating a language.
 * This class is used to encapsulate the data needed to create a language.
 */
@Data
@NoArgsConstructor
public class CreateLanguageRequestDto implements Serializable {

    /**
     * The name of the language.
     */
    @NotBlank(message = "Language is mandatory, It can't be empty")
    @NotNull(message = "Language is mandatory, It can't be null")
    String language;

    /**
     * The image associated with the language.
     */
    @NotBlank(message = "Image is mandatory, It can't be empty")
    @NotNull(message = "Image is mandatory, It can't be null")
    String image;
}
