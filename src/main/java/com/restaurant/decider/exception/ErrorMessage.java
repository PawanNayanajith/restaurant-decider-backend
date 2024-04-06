package com.restaurant.decider.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

/**
 * Represents an error message that can be returned in case of an exception.
 */
@Getter
@AllArgsConstructor
public class ErrorMessage {

    private final String message;
    private final String code;
    private final Object data;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final Date timestamp = new Date();

    /**
     * Constructs an ErrorMessage object from a GeneralStatusCodeException.
     *
     * @param exception The exception to construct the ErrorMessage from.
     */
    public ErrorMessage(GeneralStatusCodeException exception) {
        this.message = exception.getMessage();
        this.code = exception.getCode();
        this.data = exception.getData();
    }

    /**
     * Constructs an ErrorMessage object from a generic Exception.
     *
     * @param exception The exception to construct the ErrorMessage from.
     */
    public ErrorMessage(Exception exception) {
        this.message = exception.getMessage();
        this.code = "E-9999";
        this.data = null;
    }
}
