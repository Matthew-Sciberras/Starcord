package com.starcord.main.exceptions;

import org.springframework.http.HttpStatus;

public class EmailInUseException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "This email is already in use.";
    private String errorCode = "CONFLICT";
    private final HttpStatus statusCode = HttpStatus.CONFLICT;

    public EmailInUseException() { super (DEFAULT_MESSAGE); }

    public EmailInUseException(String message) { super(message); }

    public EmailInUseException(String message, String errorCode) {
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
