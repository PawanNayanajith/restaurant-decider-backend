package com.restaurant.decider.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Represents a general status code exception that can be thrown by the application.
 */
@Getter
public class GeneralStatusCodeException extends RuntimeException {

    private final String message;
    private final String code;
    private final transient Object data;
    private final HttpStatus httpStatus;

    /**
     * Constructs a GeneralStatusCodeException.
     *
     * @param message    The error message.
     * @param code       The error code.
     * @param data       Additional data related to the exception.
     * @param httpStatus The HTTP status code to be returned.
     */
    public GeneralStatusCodeException(String message, String code, Object data, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.code = code;
        this.data = data;
        this.httpStatus = httpStatus;
    }
}
