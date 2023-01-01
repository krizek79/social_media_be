package com.krizan.social_media.controller.request;

public record RegistrationRequest(
    String email,
    String username,
    String password,
    String matchingPassword
) {
}
