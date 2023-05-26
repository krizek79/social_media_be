package com.krizan.social_media.service.api;

import com.krizan.social_media.controller.request.LoginRequest;
import com.krizan.social_media.controller.request.RegistrationRequest;
import com.krizan.social_media.controller.response.AuthResponse;

public interface AuthService {

    void register(RegistrationRequest request);
    AuthResponse login(LoginRequest request);
}
