package com.jsacristan.word_card_back.services;

import com.jsacristan.word_card_back.exceptions.BaseException;
import com.jsacristan.word_card_back.models.Language;
import com.jsacristan.word_card_back.repositories.LanguageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing languages.
 */
@Slf4j
@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    /**
     * Retrieves all languages.
     *
     * @return A list of all languages.
     * @throws BaseException If an error occurs while fetching languages.
     */
    public List<Language> findAll() throws BaseException {
        try {
            log.info("Fetching all languages");
            return languageRepository.findAll();
        } catch (Exception e) {
            log.error("An error occurred while fetching languages: {}", e.getMessage());
            throw new BaseException("Error fetching languages", "LANGUAGE_FETCH_ERROR");
        }
    }

    /**
     * Retrieves a language by its ID.
     *
     * @param id The ID of the language to retrieve.
     * @return The language with the specified ID.
     * @throws BaseException If the language is not found or an error occurs while fetching the language.
     */
    public Language findById(Long id) throws BaseException {
        try {
            log.info("Fetching language with id: {}", id);
            Optional<Language> language = languageRepository.findById(id);
            return language.orElseThrow(() -> new BaseException("Language not found", "LANGUAGE_NOT_FOUND"));
        } catch (BaseException e) {
            log.warn("Language not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while fetching language with id {}: {}", id, e.getMessage());
            throw new BaseException("Error fetching language", "LANGUAGE_FETCH_ERROR");
        }
    }

    /**
     * Retrieves a language by its name.
     *
     * @param language The name of the language.
     * @return The language with the specified name.
     * @throws BaseException If the language is not found or an error occurs while fetching the language.
     */
    public Language findByLanguage(String language) throws BaseException {
        try {
            log.info("Fetching language by name: {}", language);
            Optional<Language> foundLanguage = languageRepository.findByLanguage(language);
            return foundLanguage.orElseThrow(() -> new BaseException("Language not found", "LANGUAGE_NOT_FOUND"));
        } catch (BaseException e) {
            log.warn("Language not found with name: {}", language);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while fetching language by name {}: {}", language, e.getMessage());
            throw new BaseException("Error fetching language", "LANGUAGE_FETCH_ERROR");
        }
    }

    /**
     * Saves a new language.
     *
     * @param language The language to save.
     * @return The saved language.
     * @throws BaseException If an error occurs while saving the language.
     */
    public Language save(Language language) throws BaseException {
        try {
            log.info("Saving language: {}", language);
            return languageRepository.save(language);
        } catch (Exception e) {
            log.error("An error occurred while saving language: {}", e.getMessage());
            throw new BaseException("Error saving language", "LANGUAGE_SAVE_ERROR");
        }
    }

    /**
     * Updates an existing language.
     *
     * @param id            The ID of the language to update.
     * @param languageDetails The updated language details.
     * @return The updated language.
     * @throws BaseException If the language is not found or an error occurs while updating the language.
     */
    public Language update(Long id, Language languageDetails) throws BaseException {
        try {
            log.info("Updating language with id: {}", id);
            Language language = languageRepository.findById(id)
                    .orElseThrow(() -> new BaseException("Language not found", "LANGUAGE_NOT_FOUND"));
            language.setLanguage(languageDetails.getLanguage());
            language.setImage(languageDetails.getImage());
            language.setUserid(languageDetails.getUserid());
            return languageRepository.save(language);
        } catch (BaseException e) {
            log.warn("Language not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while updating language with id {}: {}", id, e.getMessage());
            throw new BaseException("Error updating language", "LANGUAGE_UPDATE_ERROR");
        }
    }

    /**
     * Deletes a language by its ID.
     *
     * @param id The ID of the language to delete.
     * @throws BaseException If the language is not found or an error occurs while deleting the language.
     */
    public void deleteById(Long id) throws BaseException {
        try {
            log.info("Deleting language with id: {}", id);
            Language language = languageRepository.findById(id)
                    .orElseThrow(() -> new BaseException("Language not found", "LANGUAGE_NOT_FOUND"));
            languageRepository.deleteById(id);
        } catch (BaseException e) {
            log.warn("Language not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while deleting language with id {}: {}", id, e.getMessage());
            throw new BaseException("Error deleting language", "LANGUAGE_DELETE_ERROR");
        }
    }
}
