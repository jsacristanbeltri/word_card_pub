package com.jsacristan.word_card_back.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * Represents a language entity in the application.
 */
@Entity
@Data
@RequiredArgsConstructor
@Table(name="languages")
public class Language implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "image")
    private String image;

    @Column(name = "userid")
    private Long userid;
}
