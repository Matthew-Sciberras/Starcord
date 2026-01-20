package com.starcord.main.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Item not found";
    private String errorCode = "NOT_FOUND";
    private final HttpStatus statusCode = HttpStatus.NOT_FOUND;

    public NotFoundException() { super (DEFAULT_MESSAGE); }

    public NotFoundException(String message) { super(message); }

    public NotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
