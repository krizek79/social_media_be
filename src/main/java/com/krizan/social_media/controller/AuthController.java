package com.krizan.social_media.controller;

import com.krizan.social_media.controller.request.LoginRequest;
import com.krizan.social_media.controller.request.RegistrationRequest;
import com.krizan.social_media.controller.response.AuthResponse;
import com.krizan.social_media.service.api.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegistrationRequest request) {
        log.info("POST - register");
        authService.register(request);
        return "Registration successful.";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        log.info("POST - login (username/email: " + request.usernameOrEmail() + ")");
        return authService.login(request);
    }
}
