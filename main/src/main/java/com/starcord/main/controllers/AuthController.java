package com.starcord.main.controllers;

import com.starcord.main.dtos.*;
import com.starcord.main.services.AuthService;
import com.starcord.main.services.SignupService;
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
     * @return SignupResponseDTO
     * @throws Exception
     */
    @PostMapping("/signup")
    public @ResponseBody SignupResponseDTO signup(@RequestBody SignupRequestDTO request) throws Exception{
        return signupService.createUser(request);
    }

    /**
     * @param LoginRequestDTO request
     * @return LoginResponseDTO
     * @throws Exception
     */
    @PostMapping("/login")
    public @ResponseBody LoginResponseDTO login(@RequestBody LoginRequestDTO request) throws Exception {
        return authService.login(request);
    }

    /**
     * @param AuthTokenRequestDTO request
     * @return AuthTokenResponseDTO
     */
    @PostMapping("/refresh")
    public @ResponseBody AuthTokenResponseDTO refresh(@RequestBody AuthTokenRequestDTO request) {
        return authService.refreshToken(request);
    }
}
