package com.starcord.main.aspects;

import com.starcord.main.annotations.RateLimit;
import com.starcord.main.exceptions.RateLimitException;
import com.starcord.main.services.Auth.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RateLimitAspect {

    @Autowired
    private RateLimiterService rateLimiterService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Around("@annotation(com.starcord.main.annotations.RateLimit)")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);

        String clientIp = httpServletRequest.getRemoteAddr();
        System.out.println("Remote IP: " + clientIp);
        String redisKey = "ratelimit:%s:%s".formatted(clientIp, method.getName());

        boolean allowed = rateLimiterService.isAllowed(redisKey, rateLimit.limit(), rateLimit.timeWindowSeconds());

        if (!allowed) throw new RateLimitException("Too Many Requests. Please try again later.");

        return joinPoint.proceed();
    }
}