package com.starcord.main.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "User is unauthorized, please log in or sign up.";
    private String errorCode = "UNAUTHORIZED";
    private final HttpStatus statusCode = HttpStatus.UNAUTHORIZED;

    public UnauthorizedException() { super (DEFAULT_MESSAGE); }

    public UnauthorizedException(String message) { super(message); }

    public UnauthorizedException(String message, String errorCode) {
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
