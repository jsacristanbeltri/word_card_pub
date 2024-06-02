package com.jsacristan.word_card_back.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents a user entity in the application.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = true)
    private long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "level", nullable = true)
    private int level;

    @Column(name = "experience", nullable = true)
    private int experience;

    @Column(name = "log_streak", nullable = true)
    private int logStreak;

    @Column(name = "gems", nullable = true)
    private int gems;

    @Column(name = "avatar", nullable = true)
    private String avatar;
}
