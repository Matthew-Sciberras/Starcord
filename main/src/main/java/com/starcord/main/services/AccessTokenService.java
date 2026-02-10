package com.starcord.main.services;

import com.starcord.main.security.JwtService;
import com.starcord.main.utils.TimeUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AccessTokenService {
    private final JwtService jwtService;

    public AccessTokenService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String generateAccessToken(String email) {
        return jwtService.generateAccessToken(Collections.emptyMap(), email);
    }
}
