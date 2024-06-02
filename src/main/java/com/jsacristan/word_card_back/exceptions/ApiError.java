package com.jsacristan.word_card_back.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Class representing an API error.
 * This class encapsulates information about an error that occurred in the API.
 */
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {

    /**
     * The HTTP status of the error.
     */
    @NonNull
    private HttpStatus estado;

    /**
     * The timestamp when the error occurred.
     */
    @Builder.Default
    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime fecha = LocalDateTime.now();

    /**
     * The error message.
     */
    @NonNull
    private String mensaje;
}
