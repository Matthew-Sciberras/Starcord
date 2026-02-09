package com.starcord.main.services;

import com.starcord.main.dtos.*;
import com.starcord.main.exceptions.BadRequestException;
import com.starcord.main.exceptions.InvalidCredentialsException;
import com.starcord.main.models.RefreshToken;
import com.starcord.main.models.User;
import com.starcord.main.security.CustomUserDetails;
import com.starcord.main.security.JwtService;
import com.starcord.main.utils.AuthUtils;
import com.starcord.main.utils.RequestUtils;
import com.starcord.main.utils.TimeUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtils authUtils;
    private final AccessTokenService accessTokenService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final TimeUtils timeUtils;

    public AuthService(AuthenticationManager authenticationManager, AuthUtils authUtils, AccessTokenService accessTokenService, JwtService jwtService, RefreshTokenService refreshTokenService, TimeUtils timeUtils) {
        this.authenticationManager = authenticationManager;
        this.authUtils = authUtils;
        this.accessTokenService = accessTokenService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.timeUtils = timeUtils;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        String email = request.getEmail();
        String password = request.getPassword();
        String deviceID = RequestUtils.getHeader("X-Device-Id");

        if(deviceID == null || deviceID.isEmpty()) {
            throw new BadRequestException("Missing device ID");
        }

        // Authenticate
        Authentication authentication;
        try {
            Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(email, password);
            authentication = authenticationManager.authenticate(authenticationRequest);
        } catch (BadCredentialsException | InternalAuthenticationServiceException ex) {
            throw new InvalidCredentialsException();
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Refresh Token
        assert userDetails != null;
        String refreshToken = refreshTokenService.generateRefreshToken(userDetails.getUser(), deviceID);
        System.out.println("Refresh token: " + refreshToken);

        // Access Token
        String accessToken = accessTokenService.generateAccessToken(email);

        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setUserID(userDetails.getUserID());
        responseDTO.setEmail(email);
        responseDTO.setUsername(userDetails.getUserHandle());
        responseDTO.setDisplayName(userDetails.getDisplayName());
        responseDTO.setRefreshToken(refreshToken);
        responseDTO.setAccessToken(accessToken);
        return responseDTO;
    }

    public AuthTokenResponseDTO refreshToken(AuthTokenRequestDTO request) {
        String refreshToken = request.getRefreshToken();
        if(!refreshTokenService.validateRefreshToken(refreshToken)) {
            throw new InvalidCredentialsException("Invalid or Expired Refresh Token");
        }
        User user = refreshTokenService.getUserFromToken(refreshToken);
        String accessToken = accessTokenService.generateAccessToken(user.getEmail());
        long createdAt = timeUtils.convertToLong(jwtService.getIssuedAt(accessToken));
        long expiresAt = timeUtils.convertToLong(jwtService.getExpiresAt(accessToken));
        AuthTokenResponseDTO authTokenResponseDTO = new AuthTokenResponseDTO();
        authTokenResponseDTO.setToken(accessToken);
        authTokenResponseDTO.setCreatedAt(createdAt);
        authTokenResponseDTO.setExpiresAt(expiresAt);
        return authTokenResponseDTO;
    }

    public SuccessResponseDTO logout() {
        String deviceID = RequestUtils.getHeader("X-Device-Id");
        User user = authUtils.getCurrentUser();
        RefreshToken refreshToken = refreshTokenService.getTokenByUserAndDeviceID(user, deviceID);
        String token = refreshToken.getToken();
        refreshTokenService.revokeRefreshToken(token);
        return new SuccessResponseDTO("Successful Logout");
    }

    public SuccessResponseDTO logoutAll() {
        User user = authUtils.getCurrentUser();
        List<RefreshToken> refreshTokens = refreshTokenService.getTokensFromUser(user);
        for(RefreshToken refreshToken: refreshTokens) {
            String token = refreshToken.getToken();
            refreshTokenService.revokeRefreshToken(token);
        }
        return new SuccessResponseDTO("Successful Logout");
    }
}
