package com.jsacristan.word_card_back.services;

import com.jsacristan.word_card_back.dtos.CreateUserDto;
import com.jsacristan.word_card_back.exceptions.BaseException;
import com.jsacristan.word_card_back.models.User;
import com.jsacristan.word_card_back.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing users.
 */
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     * @throws BaseException If an error occurs while fetching users.
     */
    public List<User> findAll() throws BaseException {
        try {
            log.info("Fetching all users");
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("An error occurred while fetching users: {}", e.getMessage());
            throw new BaseException("Error fetching users", "USER_FETCH_ERROR");
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user with the specified ID.
     * @throws BaseException If the user is not found or an error occurs while fetching the user.
     */
    public User findById(Long id) throws BaseException {
        try {
            log.info("Fetching user with id: {}", id);
            Optional<User> user = userRepository.findById(id);
            return user.orElseThrow(() -> new BaseException("User not found", "USER_NOT_FOUND"));
        } catch (BaseException e) {
            log.warn("User not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while fetching user with id {}: {}", id, e.getMessage());
            throw new BaseException("Error fetching user", "USER_FETCH_ERROR");
        }
    }

    /**
     * Retrieves a user by their username.
     *
     * @param usernameRequest The username of the user to retrieve.
     * @return The user with the specified username.
     * @throws BaseException If the user is not found or an error occurs while fetching the user.
     */
    public User findByUsername(String usernameRequest) throws BaseException {
        try {
            log.info("Fetching user with username: {}", usernameRequest);
            Optional<User> user = userRepository.findByUsername(usernameRequest);
            return user.orElseThrow(() -> new BaseException(
                    HttpStatus.NOT_FOUND.toString(),
                    String.format("User with username %s not found", usernameRequest)));
        } catch (BaseException e) {
            log.warn("User not found with username: {}", usernameRequest);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while fetching user with username {}: {}", usernameRequest, e.getMessage());
            throw new BaseException(HttpStatus.NOT_FOUND.toString(),
                    String.format("An error occurred while fetching user with username %s: %s", usernameRequest, e.getMessage()));
        }
    }

    /**
     * Saves a new user.
     *
     * @param userRequest The user to save.
     * @return The saved user.
     * @throws BaseException If an error occurs while saving the user.
     */
    public User save(CreateUserDto userRequest) throws BaseException {
        try {
            log.info("Saving user: {}", userRequest);
            User userToSave = User.builder()
                    .username(userRequest.getUsername())
                    .email(userRequest.getEmail())
                    .avatar(userRequest.getAvatar())
                    .build();
            return userRepository.save(userToSave);
        } catch (Exception e) {
            log.error("An error occurred while saving user: {}", e.getMessage());
            throw new BaseException("Error saving user", "USER_SAVE_ERROR");
        }
    }

    /**
     * Updates an existing user.
     *
     * @param id          The ID of the user to update.
     * @param userDetails The updated user details.
     * @return The updated user.
     * @throws BaseException If the user is not found or an error occurs while updating the user.
     */
    public User update(Long id, User userDetails) throws BaseException {
        try {
            log.info("Updating user with id: {}", id);
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new BaseException("User not found", "USER_NOT_FOUND"));
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setLevel(userDetails.getLevel());
            user.setExperience(userDetails.getExperience());
            user.setLogStreak(userDetails.getLogStreak());
            user.setGems(userDetails.getGems());
            user.setAvatar(userDetails.getAvatar());
            return userRepository.save(user);
        } catch (BaseException e) {
            log.warn("User not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while updating user with id {}: {}", id, e.getMessage());
            throw new BaseException("Error updating user", "USER_UPDATE_ERROR");
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @throws BaseException If the user is not found or an error occurs while deleting the user.
     */
    public void deleteById(Long id) throws BaseException {
        try {
            log.info("Deleting user with id: {}", id);
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new BaseException("User not found", "USER_NOT_FOUND"));
            userRepository.deleteById(id);
        } catch (BaseException e) {
            log.warn("User not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while deleting user with id {}: {}", id, e.getMessage());
            throw new BaseException("Error deleting user", "USER_DELETE_ERROR");
        }
    }
}
