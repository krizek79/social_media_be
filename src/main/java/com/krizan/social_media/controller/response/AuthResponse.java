package com.krizan.social_media.controller.response;

import com.krizan.social_media.model.Role;
import java.time.Instant;
import lombok.Builder;

@Builder
public record AuthResponse (
    String username,
    String avatarUrl,
    Role role,
    String authenticationToken,
    Instant expiresAt
) {
}
