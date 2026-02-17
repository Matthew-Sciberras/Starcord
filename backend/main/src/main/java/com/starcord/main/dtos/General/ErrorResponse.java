package com.starcord.main.dtos.General;

import java.time.Instant;

public class ErrorResponse {

    private final String errorCode; //Ex: NOT_FOUND
    private final String message;
    private final int status;
    private final Instant timestamp;

    public ErrorResponse(String errorCode, String message, int status, Instant timestamp) {
        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getErrorCode() { return errorCode; }

    public String getMessage() { return message; }

    public int getStatus() { return status; }

    public Instant getTimestamp() { return timestamp; }
}