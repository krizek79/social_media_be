package com.krizan.social_media.controller.request;

public record UpdateAppUserRequest(
    String bio,
    String avatarUrl
) {
}
