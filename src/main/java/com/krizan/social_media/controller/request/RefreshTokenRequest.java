package com.krizan.social_media.controller.request;

public record RefreshTokenRequest(
    String username,
    String refreshToken
) {
}
