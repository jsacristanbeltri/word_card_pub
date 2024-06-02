package com.jsacristan.word_card_back.dtos;

import lombok.*;

import java.io.Serializable;

/**
 * Data Transfer Object for creating a user.
 * This class is used to encapsulate the data needed to create a user.
 */
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto implements Serializable {

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The email of the user.
     */
    private String email;

    /**
     * The avatar of the user.
     */
    private String avatar;
}
