package com.krizan.social_media.service.impl;

import com.krizan.social_media.controller.exception.UnauthorizedException;
import com.krizan.social_media.controller.request.LoginRequest;
import com.krizan.social_media.controller.request.RegistrationRequest;
import com.krizan.social_media.controller.response.AppUserResponse;
import com.krizan.social_media.controller.response.AuthResponse;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Role;
import com.krizan.social_media.service.api.AppUserService;
import com.krizan.social_media.service.api.AuthService;
import com.krizan.social_media.service.api.JwtService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AppUserService appUserService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void register(RegistrationRequest request) {
        appUserService.createAppUser(request, Role.USER);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        AppUser principal = appUserService
            .getAppUserByUsernameOrEmail(request.usernameOrEmail())
            .orElseThrow(UnauthorizedException::new);
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                principal.getUsername(),
                request.password()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateTokenByAuthentication(authentication);

        return AuthResponse.builder()
            .token(token)
            .expiresAt(
                Instant.now()
                    .plusMillis(jwtService.getJwtExpirationTimeInMillis())
                    .toString()
            )
            .user(new AppUserResponse(principal))
            .build();
    }
}
