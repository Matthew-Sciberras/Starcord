package com.starcord.main.services;

import com.starcord.main.dtos.*;
import com.starcord.main.exceptions.InvalidCredentialsException;
import com.starcord.main.models.RefreshToken;
import com.starcord.main.models.User;
import com.starcord.main.security.CustomUserDetails;
import com.starcord.main.security.JwtService;
import com.starcord.main.utils.TimeUtils;
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
    private final AccessTokenService accessTokenService;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final TimeUtils timeUtils;

    public AuthService(AuthenticationManager authenticationManager, AccessTokenService accessTokenService, CustomUserDetailsService userDetailsService, JwtService jwtService, RefreshTokenService refreshTokenService, TimeUtils timeUtils) {
        this.authenticationManager = authenticationManager;
        this.accessTokenService = accessTokenService;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.timeUtils = timeUtils;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        String email = request.getEmail();
        String password = request.getPassword();

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
        String refreshToken = refreshTokenService.generateRefreshToken(userDetails.getUser());
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

    public SuccessResponseDTO logout(String jwt) {
        String email = jwtService.extractEmail(jwt);
        User user = userDetailsService.loadUserByEmail(email);
        List<RefreshToken> tokens = refreshTokenService.getTokensFromUser(user);
        if(tokens.isEmpty()) {
            throw new InvalidCredentialsException("Invalid Token Entered - No session Found");
        }

        for(RefreshToken refreshToken : tokens) {
            String token = refreshToken.getToken();
            refreshTokenService.revokeRefreshToken(token);
        }
        return new SuccessResponseDTO("Successful Logout");
    }
}
