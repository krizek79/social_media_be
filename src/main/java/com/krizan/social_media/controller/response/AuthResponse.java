package com.krizan.social_media.controller.response;

import lombok.Builder;

@Builder
public record AuthResponse (
    AppUserResponse user,
    String token,
    String expiresAt
) {
}
