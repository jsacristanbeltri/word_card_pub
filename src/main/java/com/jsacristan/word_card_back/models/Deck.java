package com.jsacristan.word_card_back.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a deck entity in the application.
 */
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="decks")
public class Deck implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "username_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cards;

    /**
     * Constructs a new Deck with the specified attributes.
     *
     * @param name        the name of the deck
     * @param description the description of the deck
     * @param user        the user who owns the deck
     * @param language    the language of the deck
     * @param cards       the list of cards belonging to the deck
     */
    public Deck(String name, String description, User user, Language language, List<Card> cards) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.language = language;
        this.cards = cards;
    }
}
