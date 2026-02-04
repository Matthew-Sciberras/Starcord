package com.starcord.main.exceptions;

import org.springframework.http.HttpStatus;

public class UsernameInUseException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "This username is already in use.";
    private String errorCode = "CONFLICT";
    private final HttpStatus statusCode = HttpStatus.CONFLICT;

    public UsernameInUseException() { super (DEFAULT_MESSAGE); }

    public UsernameInUseException(String message) { super(message); }

    public UsernameInUseException(String message, String errorCode) {
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
