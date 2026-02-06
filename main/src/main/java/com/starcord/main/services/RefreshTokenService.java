package com.starcord.main.services;

import com.starcord.main.exceptions.InvalidCredentialsException;
import com.starcord.main.models.RefreshToken;
import com.starcord.main.models.User;
import com.starcord.main.repositories.RefreshTokenRepository;
import com.starcord.main.security.JwtService;
import com.starcord.main.utils.HashingUtils;
import com.starcord.main.utils.TimeUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final TimeUtils timeUtils;
    private final HashingUtils hashingUtils;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtService jwtService, TimeUtils timeUtils, HashingUtils hashingUtils) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
        this.timeUtils = timeUtils;
        this.hashingUtils = hashingUtils;
    }

    public String generateRefreshToken(User user, String deviceID) {
        String id = String.valueOf(UUID.randomUUID());
        String plainToken = jwtService.generateRefreshToken(Collections.emptyMap(), id);
        String hashedToken = hashingUtils.convertToSha256(plainToken);
        Instant expiresAt = timeUtils.convertToInstant(jwtService.getExpiresAt(plainToken));
        Instant createdAt = timeUtils.convertToInstant(jwtService.getIssuedAt(plainToken));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(id);
        refreshToken.setUser(user);
        refreshToken.setToken(hashedToken);
        refreshToken.setExpiresAt(expiresAt);
        refreshToken.setCreatedAt(createdAt);
        refreshToken.setDeviceID(deviceID);
        refreshTokenRepository.save(refreshToken);

        return plainToken;
    }

    public boolean validateRefreshToken(String plainToken) {
        String token = hashingUtils.convertToSha256(plainToken);
        if(!refreshTokenRepository.existsByToken(token)) {
            return false;
        }

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new InvalidCredentialsException(("Invalid Token Provided")));

        if(timeUtils.isExpired(refreshToken.getExpiresAt())) {
            return false;
        }

        return !refreshToken.isRevoked();
    }

    public void revokeRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new InvalidCredentialsException(("Invalid Token Provided")));
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    public User getUserFromToken(String plainToken) {
        String token = hashingUtils.convertToSha256(plainToken);
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new InvalidCredentialsException(("Invalid Token Provided")));
        return refreshToken.getUser();
    }

    public RefreshToken getRefreshToken(String plainToken) {
        String token = hashingUtils.convertToSha256(plainToken);
        return refreshTokenRepository.findByToken(token).orElseThrow(() -> new InvalidCredentialsException(("Invalid Token Provided")));
    }

    public List<RefreshToken> getTokensFromUser(User user) {
        return refreshTokenRepository.findAllByUser(user);
    }

    public RefreshToken getTokenByUserAndDeviceID(User user, String deviceID) {
        return refreshTokenRepository.findByUserAndDeviceID(user, deviceID).orElseThrow(() -> new InvalidCredentialsException(("Invalid Token or Device ID Provided")));
    }
}
