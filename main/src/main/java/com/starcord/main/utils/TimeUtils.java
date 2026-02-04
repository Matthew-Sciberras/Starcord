package com.starcord.main.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Component
public class TimeUtils {
    public long getCurrentTimestamp() {
        return Instant.now().getEpochSecond();
    }
    public Instant convertToInstant(Date date) { return date.toInstant(); }
    public long convertToLong(Date date) { return date.toInstant().getEpochSecond(); }
    public boolean isExpired(Instant expireyDate) {
        return Instant.now().isAfter(expireyDate);
    }
}
