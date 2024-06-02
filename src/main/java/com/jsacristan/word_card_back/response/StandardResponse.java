package com.jsacristan.word_card_back.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jsacristan.word_card_back.dtos.ErrorDetailsDto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * StandardResponse represents a standard response structure for API responses.
 */
public class StandardResponse implements Serializable {

    @JsonProperty("timestamp")
    private Instant timestamp;

    @JsonProperty("status")
    private String status;

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("details")
    private List<ErrorDetailsDto> errorDetails;

    /**
     * Constructs a StandardResponse with status and message.
     *
     * @param status  The status of the response.
     * @param message The message of the response.
     */
    public StandardResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Constructs a StandardResponse with status, message, and timestamp.
     *
     * @param status    The status of the response.
     * @param message   The message of the response.
     * @param timestamp The timestamp of the response.
     */
    public StandardResponse(String status, String message, Instant timestamp) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    /**
     * Constructs a StandardResponse with status, message, timestamp, and UUID.
     *
     * @param status    The status of the response.
     * @param message   The message of the response.
     * @param timestamp The timestamp of the response.
     * @param uuid      The UUID of the response.
     */
    public StandardResponse(String status, String message, Instant timestamp, String uuid) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.uuid = uuid;
    }

    /**
     * Constructs a StandardResponse with status, code, message, timestamp, UUID, and error details.
     *
     * @param status       The status of the response.
     * @param code         The code of the response.
     * @param message      The message of the response.
     * @param timestamp    The timestamp of the response.
     * @param uuid         The UUID of the response.
     * @param errorDetails The error details of the response.
     */
    public StandardResponse(String status, String code, String message, Instant timestamp, String uuid, List<ErrorDetailsDto> errorDetails) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
        this.uuid = uuid;
        this.errorDetails = errorDetails;
    }
}
