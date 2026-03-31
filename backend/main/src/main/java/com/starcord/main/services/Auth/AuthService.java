package com.starcord.main.services.Auth;

import com.starcord.main.dtos.Auth.AuthTokenResponse;
import com.starcord.main.dtos.Auth.LoginRequest;
import com.starcord.main.dtos.Auth.LoginResponse;
import com.starcord.main.dtos.General.SuccessResponse;
import com.starcord.main.exceptions.BadRequestException;
import com.starcord.main.exceptions.InvalidCredentialsException;
import com.starcord.main.models.RefreshToken;
import com.starcord.main.models.User;
import com.starcord.main.security.CustomUserDetails;
import com.starcord.main.security.JwtService;
import com.starcord.main.utils.AuthUtils;
import com.starcord.main.utils.RequestUtils;
import com.starcord.main.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
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

    public AuthService(AuthenticationManager authenticationManager, AuthUtils authUtils, AccessTokenService accessTokenService, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.authUtils = authUtils;
        this.accessTokenService = accessTokenService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Value("${jwt.refreshExpirationSeconds}")
    private long refreshTokenExpiration;

    public ResponseCookie generateRefreshCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false) // IMPORTANT: In prod, this should be changed to true to use HTTPS
                .sameSite("Strict")  // CSRF protection
                .path("/auth/refresh")
                .maxAge(refreshTokenExpiration)
                .build();
    }

    public LoginResponse login(LoginRequest request) {
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
        System.out.printf("Refresh token: %s%n", refreshToken);

        // Access Token
        String accessToken = accessTokenService.generateAccessToken(userDetails.getUserID());

        LoginResponse responseDTO = new LoginResponse();
        responseDTO.setUserID(userDetails.getUserID());
        responseDTO.setEmail(email);
        responseDTO.setUsername(userDetails.getUserHandle());
        responseDTO.setDisplayName(userDetails.getDisplayName());
        responseDTO.setRefreshToken(refreshToken);
        responseDTO.setAccessToken(accessToken);
        responseDTO.setCreatedAt(userDetails.getUser().getCreatedAt());
        responseDTO.setProfilePicture(userDetails.getUser().getProfilePicture());
        return responseDTO;
    }

    public AuthTokenResponse refreshToken(String refreshToken) {
        if(!refreshTokenService.validateRefreshToken(refreshToken)) {
            throw new InvalidCredentialsException("Invalid or Expired Refresh Token");
        }
        User user = refreshTokenService.getUserFromToken(refreshToken);
        String accessToken = accessTokenService.generateAccessToken(user.getID());
        long createdAt = TimeUtils.convertToLong(jwtService.getIssuedAt(accessToken));
        long expiresAt = TimeUtils.convertToLong(jwtService.getExpiresAt(accessToken));
        AuthTokenResponse authTokenResponse = new AuthTokenResponse();
        authTokenResponse.setToken(accessToken);
        authTokenResponse.setCreatedAt(createdAt);
        authTokenResponse.setExpiresAt(expiresAt);
        return authTokenResponse;
    }

    //Todo: Update to allow to log out of multiple sessions with the same device ID if there are
    public SuccessResponse logout() {
        String deviceID = RequestUtils.getHeader("X-Device-Id");
        User user = authUtils.getCurrentUser();
        RefreshToken refreshToken = refreshTokenService.getTokenByUserAndDeviceID(user, deviceID);
        String token = refreshToken.getToken();
        refreshTokenService.revokeRefreshToken(token);
        return new SuccessResponse("Successful Logout");
    }

    public SuccessResponse logoutAll() {
        User user = authUtils.getCurrentUser();
        List<RefreshToken> refreshTokens = refreshTokenService.getTokensFromUser(user);
        for(RefreshToken refreshToken: refreshTokens) {
            String token = refreshToken.getToken();
            refreshTokenService.revokeRefreshToken(token);
        }
        return new SuccessResponse("Successful Logout");
    }
}
