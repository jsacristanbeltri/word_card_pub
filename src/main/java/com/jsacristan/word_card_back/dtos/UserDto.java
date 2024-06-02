package com.jsacristan.word_card_back.dtos;

import lombok.*;

/**
 * Data Transfer Object for representing a user.
 * This class is used to encapsulate user data.
 */
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    /**
     * The unique identifier of the user.
     */
    private long id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The avatar of the user.
     */
    private String avatar;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The level of the user.
     */
    private int level;

    /**
     * The experience points of the user.
     */
    private int experience;

    /**
     * The login streak of the user.
     */
    private int logStreak;

    /**
     * The number of gems owned by the user.
     */
    private int gems;
}
