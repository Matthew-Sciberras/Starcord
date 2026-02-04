package com.starcord.main.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "An unknown error occurred.";
    private String errorCode = "INTERNAL_SERVER_ERROR";
    private final HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalServerException() { super (DEFAULT_MESSAGE); }

    public InternalServerException(String message) { super(message); }

    public InternalServerException(String message, String errorCode) {
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
