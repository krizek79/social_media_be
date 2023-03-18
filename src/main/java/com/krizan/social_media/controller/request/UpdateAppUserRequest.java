package com.krizan.social_media.controller.request;

public record UpdateAppUserRequest(
    String username,
    String email,
    String bio,
    String avatarUrl
) {
}
