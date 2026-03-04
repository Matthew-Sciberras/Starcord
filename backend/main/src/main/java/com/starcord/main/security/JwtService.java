package com.starcord.main.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.accessExpiration}")
    private long accessTokenExperiation; // Milliseconds

    @Value("${jwt.refreshExpiration}")
    private long refreshTokenExpiration;

    public String generateAccessToken(Map<String, Object> claims, String subject) {
        Map<String, Object> mutableClaims = new HashMap<>(claims);
        mutableClaims.put("type", "access");
        return generateJWT(mutableClaims, subject, accessTokenExperiation);
    }

    public String generateRefreshToken(Map<String, Object> claims, String subject) {
        Map<String, Object> mutableClaims = new HashMap<>(claims);
        mutableClaims.put("type", "refresh");
        return generateJWT(mutableClaims, subject, refreshTokenExpiration);
    }

    public String generateJWT(Map<String, Object> claims, String subject, long expiration) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getPrivateSigningKey())
                .compact();
    }

    private SecretKey getPrivateSigningKey() {
        byte[] keyBytes = this.secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getPrivateSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    public long extractUserID(String token) {
        return Long.parseLong(extractAllClaims(token).getSubject());
    }

    public Date getIssuedAt(String token) {
        return extractAllClaims(token).getIssuedAt();
    }

    public Date getExpiresAt(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public boolean isAccessToken(String token) {
        Claims claims = extractAllClaims(token);
        return "access".equals(claims.get("type", String.class));
    }

    public boolean isRefreshToken(String token) {
        Claims claims = extractAllClaims(token);
        return "refresh".equals(claims.get("type", String.class));
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
