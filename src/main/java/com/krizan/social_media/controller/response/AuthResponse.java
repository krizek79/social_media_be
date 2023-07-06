package com.krizan.social_media.controller.response;

import java.time.Instant;
import lombok.Builder;

@Builder
public record AuthResponse (
    AppUserResponse appUserResponse,
    String authenticationToken,
    Instant expiresAt
) {
}
