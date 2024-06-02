package com.jsacristan.word_card_back.exceptions;

import lombok.Data;

/**
 * Base exception class for custom exceptions.
 * This class extends Exception and provides additional functionality for custom exceptions.
 */
@Data
public class BaseException extends Exception{

    /**
     * The error code associated with the exception.
     */
    private String code;

    /**
     * Constructs a new BaseException with no detail message or error code.
     */
    public BaseException() {
        super();
    }

    /**
     * Constructs a new BaseException with the specified error code and no detail message.
     *
     * @param code the error code
     */
    public BaseException(final String code) {
        super();
        this.code = code;
    }

    /**
     * Constructs a new BaseException with the specified detail message, error code, and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     * @param code    the error code
     */
    public BaseException(String message, final String code) {
        super(message);
        this.code = code;
    }
}
