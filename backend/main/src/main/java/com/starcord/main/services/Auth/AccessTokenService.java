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

    public String generateAccessToken(String email, long userID) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userID", userID);
        return jwtService.generateAccessToken(claims, email);
    }

    public String generateAccessToken(String email) {
        return jwtService.generateAccessToken(Collections.emptyMap(), email);
    }
}
