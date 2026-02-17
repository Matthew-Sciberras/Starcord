package com.starcord.main.utils;

import org.springframework.stereotype.Component;

@Component
public class IdUtils {
    private final long epoch = 1705689600000L; // custom epoch, e.g., Jan 1, 2026
    private long lastTimestamp = -1L;
    private long sequence = 0L;

    public synchronized long generateId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp == lastTimestamp) {
            sequence++;
        } else {
            sequence = 0;
            lastTimestamp = timestamp;
        }

        // Compose ID: shift timestamp and add sequence
        return ((timestamp - epoch) << 12) | sequence;
    }
}
