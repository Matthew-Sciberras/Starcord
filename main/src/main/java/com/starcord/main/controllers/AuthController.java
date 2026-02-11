package com.starcord.main.controllers;

import com.starcord.main.dtos.Auth.*;
import com.starcord.main.dtos.General.SuccessResponse;
import com.starcord.main.services.Auth.AuthService;
import com.starcord.main.services.Auth.SignupService;
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
     * @param SignupRequestDTO request
     * @return SignupResponse
     * @throws Exception
     */
    @PostMapping("/signup")
    public @ResponseBody SignupResponse signup(@RequestBody SignupRequest request){
        return signupService.createUser(request);
    }

    /**
     * @param LoginRequestDTO request
     * @param RequestHeader "X-Device-Id"
     * @return LoginResponse
     * @throws Exception
     */
    @PostMapping("/login")
    public @ResponseBody LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /**
     * @return SuccessResponse
     */
    @PostMapping("/logout")
    public @ResponseBody SuccessResponse logout() {
        return authService.logout();
    }

    /**
     * @return SuccessResponse
     */
    @PostMapping("/logoutAll")
    public @ResponseBody SuccessResponse logoutAll() {
        return authService.logoutAll();
    }

    /**
     * @param AuthTokenRequestDTO request
     * @return AuthTokenResponse
     */
    @PostMapping("/refresh")
    public @ResponseBody AuthTokenResponse refresh(@RequestBody AuthTokenRequest request) {
        return authService.refreshToken(request);
    }

}
