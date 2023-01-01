package com.krizan.social_media.controller.request;

public record LoginRequest(
    String email,
    String password
) {
}
