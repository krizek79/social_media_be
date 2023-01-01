package com.krizan.social_media.controller.response;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {

    private final String username;
    private final String authenticationToken;
    private final String refreshToken;
    private final Instant expiresAt;
}
