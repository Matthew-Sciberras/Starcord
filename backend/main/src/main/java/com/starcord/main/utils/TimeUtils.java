package com.starcord.main.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

public class TimeUtils {
    public static long getCurrentTimestamp() {
        return Instant.now().getEpochSecond();
    }
    public static Instant convertToInstant(Date date) { return date.toInstant(); }
    public static long convertToLong(Date date) { return date.toInstant().getEpochSecond(); }
    public static boolean isExpired(Instant expiryDate) {
        return Instant.now().isAfter(expiryDate);
    }
}
