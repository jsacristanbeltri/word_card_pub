package com.jsacristan.word_card_back.repositories;

import com.jsacristan.word_card_back.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by username.
     *
     * @param username The username of the user.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findByUsername(String username);
}
