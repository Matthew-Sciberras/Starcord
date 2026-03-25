package com.starcord.main.dtos.General;

import java.time.Instant;

public class SuccessResponse {
    private final String message;
    private final int status;
    private final Object data;
    private final Instant timestamp = Instant.now();

    public SuccessResponse(String message) {
        this.message = message;
        this.status = 200;
        this.data = null;
    }

    public SuccessResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.data = null;
    }

    public SuccessResponse(String message, int status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() { return message; }
    public int getStatus() { return status; }
    public Instant getTimestamp() { return timestamp; }
    public Object getData() { return data; }
}
