package com.restaurant.decider.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler to handle exceptions thrown by the application controllers.
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Handles GeneralStatusCodeException and returns an appropriate ResponseEntity.
     *
     * @param exception The exception to be handled.
     * @return ResponseEntity with the appropriate error message and status code.
     */
    @ExceptionHandler(value = GeneralStatusCodeException.class)
    public ResponseEntity<Object> handleGeneralStatusCodeException(GeneralStatusCodeException exception) {
        return new ResponseEntity<>(new ErrorMessage(exception), HttpStatusCode.valueOf(exception.getHttpStatus().value()));
    }

    /**
     * Handles generic Exception and returns an internal server error response.
     *
     * @param exception The exception to be handled.
     * @return ResponseEntity with internal server error message and status code.
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        return new ResponseEntity<>(new ErrorMessage(exception), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}