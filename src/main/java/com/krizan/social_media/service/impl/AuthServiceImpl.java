package com.krizan.social_media.service.impl;

import com.krizan.social_media.configuration.JwtProvider;
import com.krizan.social_media.controller.exception.NotFoundException;
import com.krizan.social_media.controller.request.LoginRequest;
import com.krizan.social_media.controller.request.RefreshTokenRequest;
import com.krizan.social_media.controller.request.RegistrationRequest;
import com.krizan.social_media.controller.response.AuthResponse;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.RefreshToken;
import com.krizan.social_media.model.Role;
import com.krizan.social_media.repository.RefreshTokenRepository;
import com.krizan.social_media.service.api.AppUserService;
import com.krizan.social_media.service.api.AuthService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AppUserService appUserService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public void register(RegistrationRequest request) {
        appUserService.createAppUser(request, Role.USER);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateTokenByAuthentication(authentication);

        return AuthResponse.builder()
            .authenticationToken(token)
            .refreshToken(generateRefreshToken().getToken())
            .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationTimeInMillis()))
            .username(request.username())
            .build();
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        validateRefreshToken(request.refreshToken());
        AppUser appUser = appUserService.getAppUserByUsername(request.username());
        String token = jwtProvider.generateTokenByAppUser(appUser);
        return AuthResponse.builder()
            .authenticationToken(token)
            .refreshToken(request.refreshToken())
            .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationTimeInMillis()))
            .username(request.username())
            .build();
    }

    @Override
    public RefreshToken generateRefreshToken() {
        return refreshTokenRepository.save(new RefreshToken());
    }

    // TODO: make exception for not valid refresh token
    @Override
    public void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token).orElseThrow(
            () -> new NotFoundException("Token is not valid.")
        );
    }
}
