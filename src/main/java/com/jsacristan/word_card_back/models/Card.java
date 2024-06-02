package com.jsacristan.word_card_back.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

/**
 * Represents a card entity in the application.
 */
@Entity
@Table(name="cards")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "name1")
    private String name1;

    @Column(name = "name2")
    private String name2;

    @Column(name = "enable")
    private boolean enable;

    @Column(name = "lastTry")
    private Instant lastTry;

    @Column(name = "periodDaysReminder")
    private Integer periodDaysReminder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "deck_id", nullable = false)
    @JsonIgnore
    private Deck deck;

    /**
     * Constructs a new Card with the specified attributes.
     *
     * @param name1             the name of the card (side 1)
     * @param name2             the name of the card (side 2)
     * @param enable            indicates whether the card is enabled or disabled
     * @param lastTry           the instant of the last attempt to review the card
     * @param periodDaysReminder the period in days for reminder
     * @param deck              the deck to which the card belongs
     */
    public Card(String name1, String name2, boolean enable, Instant lastTry, Integer periodDaysReminder, Deck deck) {
        this.name1 = name1;
        this.name2 = name2;
        this.enable = enable;
        this.lastTry = lastTry;
        this.periodDaysReminder = periodDaysReminder;
        this.deck = deck;
    }
}
