package com.starcord.main.dtos;

import java.time.Instant;

public class SuccessResponseDTO {
    private final String message;
    private final int status;
    private final Instant timestamp = Instant.now();

    public SuccessResponseDTO(String message) {
        this.message = message;
        this.status = 200;
    }

    public SuccessResponseDTO(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() { return message; }
    public int getStatus() { return status; }
    public Instant getTimestamp() { return timestamp; }
}
