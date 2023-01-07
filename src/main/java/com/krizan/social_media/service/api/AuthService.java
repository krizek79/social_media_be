package com.krizan.social_media.service.api;

import com.krizan.social_media.controller.request.LoginRequest;
import com.krizan.social_media.controller.request.RefreshTokenRequest;
import com.krizan.social_media.controller.request.RegistrationRequest;
import com.krizan.social_media.controller.response.AuthResponse;
import com.krizan.social_media.model.RefreshToken;

public interface AuthService {

    void register(RegistrationRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
    RefreshToken generateRefreshToken();
    void validateRefreshToken(String token);
}
