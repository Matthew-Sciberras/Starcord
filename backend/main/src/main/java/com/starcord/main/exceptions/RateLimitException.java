package com.starcord.main.exceptions;

import org.springframework.http.HttpStatus;

public class RateLimitException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "You have been ratelimited. Please try again later.";
    private String errorCode = "TOO_MANY_REQUESTS";
    private final HttpStatus statusCode = HttpStatus.TOO_MANY_REQUESTS;

    public RateLimitException() { super (DEFAULT_MESSAGE); }

    public RateLimitException(String message) {
        super(message);
    }

    public RateLimitException(String message, String errorCode) {
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
