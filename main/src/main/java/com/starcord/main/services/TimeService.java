package com.starcord.main.services;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TimeService {
    public long getCurrentTimestamp() {
        return Instant.now().getEpochSecond();
    }
}
