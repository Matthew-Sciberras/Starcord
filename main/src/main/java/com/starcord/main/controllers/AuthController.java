package com.starcord.main.controllers;

import com.starcord.main.dtos.*;
import com.starcord.main.services.AuthService;
import com.starcord.main.services.SignupService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

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
    public @ResponseBody SignupResponseDTO signup(@RequestBody SignupRequestDTO request){
        return signupService.createUser(request);
    }

    /**
     * @param LoginRequestDTO request
     * @param "X-Device-Id" deviceId
     * @return LoginResponseDTO
     * @throws Exception
     */
    @PostMapping("/login")
    public @ResponseBody LoginResponseDTO login(@RequestBody LoginRequestDTO request, @RequestHeader("X-Device-Id") String deviceId) {
        return authService.login(request, deviceId);
    }

    /**
     * @return SuccessResponseDTO
     */
    @PostMapping("/logout")
    public @ResponseBody SuccessResponseDTO logout(HttpServletRequest request) {
        return authService.logout(request);
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
