package com.jsacristan.word_card_back.repositories;

import com.jsacristan.word_card_back.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Card entities.
 */
public interface CardRepository extends JpaRepository<Card, Long> {
}
