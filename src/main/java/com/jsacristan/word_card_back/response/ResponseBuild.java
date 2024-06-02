package com.jsacristan.word_card_back.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * ResponseBuild provides methods for building ResponseEntity<ApiResponse>.
 */
@Component
public class ResponseBuild {
    /**
     * Build a response with custom HttpHeaders, HTTP status code, message, data, and other parameters.
     *
     * @param httpHeader     The HttpHeaders object.
     * @param httpStatusCode The HTTP status code.
     * @param message        The message.
     * @param data           The data object.
     * @param otherParams    Additional parameters.
     * @return ResponseEntity<ApiResponse>.
     */
    public ResponseEntity<ApiResponse> buildResponse(
            HttpHeaders httpHeader, int httpStatusCode, String message, Object data, Map<String, Object> otherParams) {
        return new ApiResponse.ApiResponseBuilder<>(httpStatusCode, message).withHttpHeader(httpHeader)
                .withData(data).withOtherParams(otherParams).build();
    }

    /**
     * Build a response with HTTP status code, message, data, and other parameters.
     *
     * @param httpStatusCode The HTTP status code.
     * @param message        The message.
     * @param data           The data object.
     * @param otherParams    Additional parameters.
     * @return ResponseEntity<ApiResponse>.
     */
    public ResponseEntity<ApiResponse> buildResponse(
            int httpStatusCode, String message, Object data, Map<String, Object> otherParams) {
        return new ApiResponse.ApiResponseBuilder<>(httpStatusCode, message)
                .withData(data).withOtherParams(otherParams).build();
    }

    /**
     * Build a response with HTTP status code, message, and other parameters.
     *
     * @param httpStatusCode The HTTP status code.
     * @param message        The message.
     * @param otherParams    Additional parameters.
     * @return ResponseEntity<ApiResponse>.
     */
    public ResponseEntity<ApiResponse> buildResponse(
            int httpStatusCode, String message, Map<String, Object> otherParams) {
        return new ApiResponse.ApiResponseBuilder<>(httpStatusCode, message)
                .withOtherParams(otherParams).build();
    }

    /**
     * Build a response with custom HttpHeaders, HTTP status code, message, and data.
     *
     * @param httpHeader     The HttpHeaders object.
     * @param httpStatusCode The HTTP status code.
     * @param message        The message.
     * @param data           The data object.
     * @return ResponseEntity<ApiResponse>.
     */
    public ResponseEntity<ApiResponse> buildResponse(
            HttpHeaders httpHeader, int httpStatusCode, String message, Object data) {
        return new ApiResponse.ApiResponseBuilder<>(httpStatusCode, message)
                .withHttpHeader(httpHeader).withData(data).build();
    }

    /**
     * Build a response with custom HttpHeaders, HTTP status code, message, and other parameters.
     *
     * @param httpHeader     The HttpHeaders object.
     * @param httpStatusCode The HTTP status code.
     * @param message        The message.
     * @param otherParams    Additional parameters.
     * @return ResponseEntity<ApiResponse>.
     */
    public ResponseEntity<ApiResponse> buildResponse(
            HttpHeaders httpHeader, int httpStatusCode, String message, Map<String, Object> otherParams) {
        return new ApiResponse.ApiResponseBuilder<>(httpStatusCode, message)
                .withHttpHeader(httpHeader).withOtherParams(otherParams).build();
    }

    /**
     * Build a response with custom HttpHeaders, HTTP status code, and message.
     *
     * @param httpHeader     The HttpHeaders object.
     * @param httpStatusCode The HTTP status code.
     * @param message        The message.
     * @return ResponseEntity<ApiResponse>.
     */
    public ResponseEntity<ApiResponse> buildResponse(
            HttpHeaders httpHeader, int httpStatusCode, String message) {
        return new ApiResponse.ApiResponseBuilder<>(httpStatusCode, message)
                .withHttpHeader(httpHeader).build();
    }

    /**
     * Build a response with HTTP status code, message, and data.
     *
     * @param httpStatusCode The HTTP status code.
     * @param message        The message.
     * @param data           The data object.
     * @return ResponseEntity<ApiResponse>.
     */
    public ResponseEntity<ApiResponse> buildResponse(
            int httpStatusCode, String message, Object data) {
        return new ApiResponse.ApiResponseBuilder<>(httpStatusCode, message)
                .withData(data).build();
    }

    /**
     * Build a response with HTTP status code and message.
     *
     * @param httpStatusCode The HTTP status code.
     * @param message        The message.
     * @return ResponseEntity<ApiResponse>.
     */
    public ResponseEntity<ApiResponse> buildResponse(int httpStatusCode, String message) {
        return new ApiResponse.ApiResponseBuilder<>(httpStatusCode, message).build();
    }
}
