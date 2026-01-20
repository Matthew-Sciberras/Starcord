package com.starcord.main.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Username or password invalid.";
    private String errorCode = "UNAUTHORIZED";
    private final HttpStatus statusCode = HttpStatus.UNAUTHORIZED;

    public InvalidCredentialsException() {
        super (DEFAULT_MESSAGE);
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(String message, String errorCode) {
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
