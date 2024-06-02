package com.jsacristan.word_card_back.controller;

import com.jsacristan.word_card_back.exceptions.BaseException;
import com.jsacristan.word_card_back.models.Language;
import com.jsacristan.word_card_back.response.ResponseBuild;
import com.jsacristan.word_card_back.services.LanguageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing languages.
 * Provides endpoints for CRUD operations on languages.
 */
@Slf4j
@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    private final LanguageService languageService;
    private final ResponseBuild responseBuilder;

    /**
     * Constructor for dependency injection.
     *
     * @param languageService the language service
     * @param responseBuilder the response builder
     */
    @Autowired
    public LanguageController(LanguageService languageService, ResponseBuild responseBuilder) {
        this.languageService = languageService;
        this.responseBuilder = responseBuilder;
    }

    /**
     * Retrieves all languages.
     *
     * @return an HTTP response with the status and the retrieved languages
     */
    @GetMapping
    public ResponseEntity<?> getAllLanguages() {
        try {
            log.info("Fetching all languages");
            List<Language> languages = languageService.findAll();
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Languages retrieved successfully", languages);
        } catch (BaseException e) {
            log.error("An error occurred while fetching all languages: {}", e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Retrieves a language by its ID.
     *
     * @param id the ID of the language to retrieve
     * @return an HTTP response with the status and the retrieved language
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getLanguageById(@PathVariable Long id) {
        try {
            log.info("Fetching language with id: {}", id);
            Language language = languageService.findById(id);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Language found", language);
        } catch (BaseException e) {
            log.error("An error occurred while fetching language with id {}: {}", id, e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Retrieves a language by its name.
     *
     * @param language the name of the language to retrieve
     * @return an HTTP response with the status and the retrieved language
     */
    @GetMapping("/language")
    public ResponseEntity<?> getLanguageByLanguage(@RequestParam String language) {
        try {
            log.info("Fetching language by name: {}", language);
            Language foundLanguage = languageService.findByLanguage(language);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Language found", foundLanguage);
        } catch (BaseException e) {
            log.error("An error occurred while fetching language by name {}: {}", language, e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Creates a new language.
     *
     * @param language the language to create
     * @return an HTTP response with the status and the created language
     */
    @PostMapping
    public ResponseEntity<?> createLanguage(@RequestBody Language language) {
        try {
            log.info("Creating language: {}", language);
            Language savedLanguage = languageService.save(language);
            return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Language created successfully", savedLanguage);
        } catch (BaseException e) {
            log.error("An error occurred while creating language: {}", e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Updates an existing language.
     *
     * @param id the ID of the language to update
     * @param languageDetails the details of the updated language
     * @return an HTTP response with the status and the updated language
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLanguage(@PathVariable Long id, @RequestBody Language languageDetails) {
        try {
            log.info("Updating language with id: {}", id);
            Language updatedLanguage = languageService.update(id, languageDetails);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Language updated successfully", updatedLanguage);
        } catch (BaseException e) {
            log.error("An error occurred while updating language with id {}: {}", id, e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Deletes a language by its ID.
     *
     * @param id the ID of the language to delete
     * @return an HTTP response with the status of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLanguage(@PathVariable Long id) {
        try {
            log.info("Deleting language with id: {}", id);
            languageService.deleteById(id);
            return responseBuilder.buildResponse(HttpStatus.NO_CONTENT.value(), "Language deleted successfully");
        } catch (BaseException e) {
            log.error("An error occurred while deleting language with id {}: {}", id, e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
