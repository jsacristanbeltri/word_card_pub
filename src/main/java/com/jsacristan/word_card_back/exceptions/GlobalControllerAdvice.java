package com.jsacristan.word_card_back.exceptions;

import com.jsacristan.word_card_back.dtos.ErrorDetailsDto;
import com.jsacristan.word_card_back.response.ResponseBuild;
import com.jsacristan.word_card_back.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global controller advice for handling exceptions across all controllers.
 * This class provides centralized exception handling for the application.
 */
@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @Autowired
    private ResponseBuild responseBuilder;

    /**
     * Handle method argument not valid exceptions.
     * This method handles MethodArgumentNotValidException thrown when request validation fails.
     *
     * @param ex      the exception
     * @param headers the HTTP headers
     * @param status  the HTTP status
     * @param request the web request
     * @return a ResponseEntity with error details
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final List<ErrorDetailsDto> details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    return new ErrorDetailsDto(error.getCode(), error.getField(), error.getDefaultMessage());
                }).collect(Collectors.toList());


        final StandardResponse err = new StandardResponse(
                String.valueOf(status.value()),
                status.toString(),
                "Validation Error",
                Instant.now(),"",
                details );
        return new ResponseEntity<>(err, status);
    }

    /**
     * Handle base exceptions.
     * This method handles BaseException and returns appropriate HTTP response based on the exception code.
     *
     * @param e the exception
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleBaseException (BaseException e){

        if(e.getCode().equals(HttpStatus.NOT_FOUND.toString())){
            return responseBuilder.buildResponse(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage(),
                    null
            );
        }

        if(e.getCode().equals(HttpStatus.UNAUTHORIZED.toString())){
            return responseBuilder.buildResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    e.getMessage(),
                    null
            );
        }

        if (e.getCode().equals(HttpStatus.BAD_REQUEST.toString())){
            return responseBuilder.buildResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null
            );
        }

        if (e.getCode().equals(HttpStatus.NO_CONTENT.toString())){
            return responseBuilder.buildResponse(
                    HttpStatus.NO_CONTENT.value(),
                    e.getMessage(),
                    null
            );
        }

        return responseBuilder.buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(),
                null
        );
    }

    /**
     * Build error response entity.
     * This method constructs a ResponseEntity with an ApiError body.
     *
     * @param status  the HTTP status
     * @param message the error message
     * @return a ResponseEntity with ApiError body
     */
    private ResponseEntity<ApiError> buildErrorResponseEntity(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(ApiError.builder()
                        .estado(status)
                        .mensaje(message)
                        .build());

    }


}
