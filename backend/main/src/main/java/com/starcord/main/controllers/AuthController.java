package com.starcord.main.controllers;

import com.starcord.main.annotations.RateLimit;
import com.starcord.main.dtos.Auth.*;
import com.starcord.main.dtos.General.SuccessResponse;
import com.starcord.main.mappers.AuthMapper;
import com.starcord.main.services.Auth.AuthService;
import com.starcord.main.services.Auth.SignupService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final SignupService signupService;

    public AuthController(AuthService authService, SignupService signupService) {
        this.authService = authService;
        this.signupService = signupService;
    }


    /**
     * @param request SignupRequest
     * @return SignupResponse
     */
    @RateLimit(limit = 10)
    @PostMapping("/signup")
    public @ResponseBody SignupResponse signup(@RequestBody SignupRequest request){
        return signupService.createUser(request);
    }

    /**
     * @param request LoginRequest
     * @return LoginResponse
     */
    @RateLimit(limit = 10)
    @PostMapping("/login")
    public @ResponseBody LoginPublicResponse login(@RequestBody LoginRequest request, HttpServletResponse response) {
        LoginResponse loginResponse = authService.login(request);
        ResponseCookie cookie = authService.generateRefreshCookie(loginResponse.getRefreshToken());

        response.addHeader("Set-Cookie", cookie.toString());
        return AuthMapper.convertToPublicResponse(loginResponse);
    }

    /**
     * @return SuccessResponse
     */
    @RateLimit(limit = 10)
    @PostMapping("/logout")
    public @ResponseBody SuccessResponse logout() {
        return authService.logout();
    }

    /**
     * @return SuccessResponse
     */
    @RateLimit(limit = 10)
    @PostMapping("/logoutAll")
    public @ResponseBody SuccessResponse logoutAll() {
        return authService.logoutAll();
    }

    /**
     * @param refreshToken
     * @return AuthTokenResponse
     */
    @RateLimit(limit = 10)
    @PostMapping("/refresh")
    public @ResponseBody AuthTokenResponse refresh(@CookieValue("refreshToken") String refreshToken) {
        return authService.refreshToken(refreshToken);
    }
}
