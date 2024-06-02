package com.jsacristan.word_card_back.maps;

import com.jsacristan.word_card_back.dtos.CreateDeckRequestDto;
import com.jsacristan.word_card_back.dtos.DeckOutDto;
import com.jsacristan.word_card_back.dtos.DeckRequestDto;
import com.jsacristan.word_card_back.models.Deck;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between Deck entities and DTOs.
 */
@Mapper(componentModel = "spring")
public interface DeckMapper {

    /**
     * Convert a Deck entity to a DeckOutDto.
     * This method maps the attributes of a Deck entity to a DeckOutDto object.
     *
     * @param deck the Deck entity to convert
     * @return the corresponding DeckOutDto
     */
    @Mapping(target = "language", source = "language.language")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "numberOfCards", expression = "java(deck.getCards().size())")
    DeckOutDto deckToDeckOutDto(Deck deck);

    /**
     * Convert a CreateDeckRequestDto to a DeckRequestDto.
     * This method maps the attributes of a CreateDeckRequestDto to a DeckRequestDto object.
     *
     * @param createDeckRequest the CreateDeckRequestDto to convert
     * @return the corresponding DeckRequestDto
     */
    DeckRequestDto deckToDeckRequestDto(CreateDeckRequestDto createDeckRequest);
}
