package com.krizan.social_media.controller;

import com.krizan.social_media.controller.request.LoginRequest;
import com.krizan.social_media.controller.request.RefreshTokenRequest;
import com.krizan.social_media.controller.request.RegistrationRequest;
import com.krizan.social_media.controller.response.AuthResponse;
import com.krizan.social_media.service.api.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public record AuthController(AuthService authService) {

    @PostMapping("/register")
    public String register(@RequestBody RegistrationRequest request) {
        authService.register(request);
        return "Registration successful.";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refreshToken")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }
}