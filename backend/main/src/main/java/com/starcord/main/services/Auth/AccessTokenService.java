package com.starcord.main.services.Auth;

import com.starcord.main.security.JwtService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccessTokenService {
    private final JwtService jwtService;

    public AccessTokenService(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    public String generateAccessToken(long userId) {
        return jwtService.generateAccessToken(Collections.emptyMap(), String.valueOf(userId));
    }
}
