package com.krizan.social_media.controller.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty(message = "Username/email is mandatory.")
        String usernameOrEmail,
        String password
) {
}
