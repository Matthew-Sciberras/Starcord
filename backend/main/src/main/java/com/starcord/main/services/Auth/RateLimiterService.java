package com.starcord.main.services.Auth;

import com.starcord.main.exceptions.InternalServerException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;

    public RateLimiterService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(String key, int limit, int timeWindowSeconds) {

        Long currentCount = redisTemplate.opsForValue().increment(key);

        if (currentCount == null) {
            throw new InternalServerException(
                    "Rate limiter failed: Redis increment returned null"
            );
        }

        if (currentCount == 1L) {
            redisTemplate.expire(key, Duration.ofSeconds(timeWindowSeconds));
        }

        return currentCount <= limit;
    }
}
