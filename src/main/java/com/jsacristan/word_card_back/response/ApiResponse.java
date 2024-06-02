package com.jsacristan.word_card_back.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

/**
 * ApiResponse represents the response format for API requests.
 *
 * @param <T> The type of data included in the response.
 */
@JsonPropertyOrder({ "httpHeaders", "httpStatusCode", "message", "data", "otherParams" })
public class ApiResponse<T> {
    private final HttpHeaders httpHeaders;
    private final int httpStatusCode;
    private final String message;
    private final T data;
    private final Map<String, Object> otherParams;

    /**
     * Private constructor to enforce the use of ApiResponseBuilder.
     *
     * @param builder The ApiResponseBuilder used to construct the ApiResponse.
     */
    private ApiResponse(ApiResponseBuilder builder) {
        this.httpHeaders = builder.httpHeaders;
        this.httpStatusCode = builder.httpStatusCode;
        this.message = builder.message;
        this.data = (T) builder.data;
        this.otherParams = builder.otherParams;
    }

    /**
     * Getter for HttpHeaders.
     *
     * @return The HttpHeaders object.
     */
    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    /**
     * Getter for HTTP status code.
     *
     * @return The HTTP status code.
     */
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    /**
     * Getter for the message.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter for the data.
     *
     * @return The data.
     */
    public T getData() {
        return data;
    }

    /**
     * Getter for other parameters.
     *
     * @return The other parameters.
     */
    public Map<String, Object> getOtherParams() {
        return otherParams;
    }

    /**
     * ApiResponseBuilder provides a fluent API for constructing ApiResponse objects.
     *
     * @param <T> The type of data included in the ApiResponse.
     */
    public static class ApiResponseBuilder<T> {

        private HttpHeaders httpHeaders = new HttpHeaders();
        private final int httpStatusCode;
        private final String message;
        private T data;
        private Map<String, Object> otherParams = Collections.emptyMap();

        /**
         * Constructor for ApiResponseBuilder.
         *
         * @param httpStatusCode The HTTP status code.
         * @param message        The message.
         */
        public ApiResponseBuilder(int httpStatusCode, String message) {
            this.httpStatusCode = httpStatusCode;
            this.message = message;
        }

        /**
         * Set custom HTTP headers.
         *
         * @param httpHeader The HttpHeaders object.
         * @return This ApiResponseBuilder object.
         */
        public ApiResponseBuilder<T> withHttpHeader(HttpHeaders httpHeader) {
            this.httpHeaders = httpHeader;
            return this;
        }

        /**
         * Set the data included in the response.
         *
         * @param data The data object.
         * @return This ApiResponseBuilder object.
         */
        public ApiResponseBuilder<T> withData(T data) {
            this.data = data;
            return this;
        }

        /**
         * Set additional parameters.
         *
         * @param otherParams The map containing additional parameters.
         * @return This ApiResponseBuilder object.
         */
        public ApiResponseBuilder<T> withOtherParams(Map<String, Object> otherParams) {
            this.otherParams = otherParams;
            return this;
        }

        /**
         * Build the ApiResponse object.
         *
         * @return A ResponseEntity containing the ApiResponse.
         */
        public ResponseEntity<ApiResponse> build() {
            ApiResponse<T> apiResponse = new ApiResponse<>(this);
            return ResponseEntity.status(apiResponse.getHttpStatusCode()).headers(apiResponse.getHttpHeaders())
                    .body(apiResponse);
        }
    }
}
