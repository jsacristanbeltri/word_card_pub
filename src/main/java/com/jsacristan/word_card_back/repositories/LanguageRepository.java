package com.jsacristan.word_card_back.repositories;

import com.jsacristan.word_card_back.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Language entities.
 */
@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    /**
     * Find a language by its name.
     *
     * @param language The name of the language.
     * @return An Optional containing the language if found, otherwise empty.
     */
    Optional<Language> findByLanguage(String language);
}
