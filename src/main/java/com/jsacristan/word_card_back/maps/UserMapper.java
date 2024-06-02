package com.jsacristan.word_card_back.maps;

import com.jsacristan.word_card_back.dtos.UserDto;
import com.jsacristan.word_card_back.models.User;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between User entities and DTOs.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convert a User entity to a UserDto.
     * This method maps the attributes of a User entity to a UserDto object.
     *
     * @param user the User entity to convert
     * @return the corresponding UserDto
     */
    UserDto userToUserDto(User user);

    /**
     * Convert a UserDto to a User entity.
     * This method maps the attributes of a UserDto to a User entity object.
     *
     * @param userDto the UserDto to convert
     * @return the corresponding User entity
     */
    User userDtoToUser(UserDto userDto);

}
