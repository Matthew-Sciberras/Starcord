package com.starcord.main.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Invalid Request";
    private String errorCode = "BAD_REQUEST";
    private final HttpStatus statusCode = HttpStatus.BAD_REQUEST;

    public BadRequestException() { super (DEFAULT_MESSAGE); }

    public BadRequestException(String message) { super(message); }

    public BadRequestException(String message, String errorCode) {
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
