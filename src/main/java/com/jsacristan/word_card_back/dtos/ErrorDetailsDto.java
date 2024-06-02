package com.jsacristan.word_card_back.dtos;

/**
 * Data Transfer Object for representing error details.
 * This class is used to encapsulate details about errors that occur.
 */
public class ErrorDetailsDto {

    private String code;
    private String target;
    private String message;

    /**
     * Constructs an ErrorDetailsDto object with the specified code, target, and message.
     *
     * @param code    the error code
     * @param target  the target of the error
     * @param message the error message
     */
    public ErrorDetailsDto(String code, String target, String message) {
        this.code = code;
        this.target = target;
        this.message = message;
    }

    /**
     * Gets the error code.
     *
     * @return the error code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the error code.
     *
     * @param code the error code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the target of the error.
     *
     * @return the target of the error
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the target of the error.
     *
     * @param target the target of the error to set
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     *
     * @param message the error message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns a string representation of the ErrorDetailsDto object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ErrorDetailsDto{");
        sb.append("code='").append(code).append('\'');
        sb.append(", target='").append(target).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
