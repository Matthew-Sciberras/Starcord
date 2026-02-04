package com.starcord.main.dtos;

import java.time.Instant;

public class ErrorResponseDTO {

    private final String errorCode; //Ex: NOT_FOUND
    private final String message;
    private final int status;
    private final Instant timestamp;

    public ErrorResponseDTO(String errorCode, String message, int status, Instant timestamp) {
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