package com.jsacristan.word_card_back.controller;

import com.jsacristan.word_card_back.config.Config;
import com.jsacristan.word_card_back.dtos.CreateUserDto;
import com.jsacristan.word_card_back.exceptions.BaseException;
import com.jsacristan.word_card_back.models.User;
import com.jsacristan.word_card_back.response.ResponseBuild;
import com.jsacristan.word_card_back.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing users.
 * Provides endpoints for CRUD operations on users.
 */
@RestController
@RequestMapping(Config.API_V1_PREFIX + "/users")
@Slf4j
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final ResponseBuild responseBuilder;

    /**
     * Constructor for dependency injection.
     *
     * @param userService the user service
     * @param responseBuilder the response builder
     */
    @Autowired
    public UserController(UserService userService, ResponseBuild responseBuilder) {
        this.userService = userService;
        this.responseBuilder = responseBuilder;
    }

    /**
     * Retrieves all users.
     *
     * @return an HTTP response with the status and the retrieved users
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            log.info("Fetching all users");
            List<User> users = userService.findAll();
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Users retrieved successfully", users);
        } catch (BaseException e) {
            log.error("An error occurred while fetching all users: {}", e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param id the ID of the user to retrieve
     * @return an HTTP response with the status and the retrieved user
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            log.info("Fetching user with id: {}", id);
            User user = userService.findById(id);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "User found", user);
        } catch (BaseException e) {
            log.error("An error occurred while fetching user with id {}: {}", id, e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return an HTTP response with the status and the created user
     */
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDto user) {
        try {
            log.info("Creating user: {}", user);
            User savedUser = userService.save(user);
            return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "User created successfully", savedUser);
        } catch (BaseException e) {
            log.error("An error occurred while creating user: {}", e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Updates an existing user.
     *
     * @param id the ID of the user to update
     * @param userDetails the details of the updated user
     * @return an HTTP response with the status and the updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            log.info("Updating user with id: {}", id);
            User updatedUser = userService.update(id, userDetails);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "User updated successfully", updatedUser);
        } catch (BaseException e) {
            log.error("An error occurred while updating user with id {}: {}", id, e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * Deletes a user by its ID.
     *
     * @param id the ID of the user to delete
     * @return an HTTP response with the status of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            log.info("Deleting user with id: {}", id);
            userService.deleteById(id);
            return responseBuilder.buildResponse(HttpStatus.NO_CONTENT.value(), "User deleted successfully");
        } catch (BaseException e) {
            log.error("An error occurred while deleting user with id {}: {}", id, e.getMessage());
            return responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
