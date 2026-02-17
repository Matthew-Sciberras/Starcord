package com.starcord.main.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "You cannot access this resource.";
    private String errorCode = "FORBIDDEN";
    private final HttpStatus statusCode = HttpStatus.FORBIDDEN;

    public ForbiddenException() { super (DEFAULT_MESSAGE); }

    public ForbiddenException(String message) { super(message); }

    public ForbiddenException(String message, String errorCode) {
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
