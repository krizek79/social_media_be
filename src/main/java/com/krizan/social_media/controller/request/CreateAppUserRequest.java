package com.krizan.social_media.controller.request;

import com.krizan.social_media.model.Role;
import jakarta.validation.constraints.NotNull;

public record CreateAppUserRequest(
        @NotNull
        RegistrationRequest registrationRequest,
        @NotNull
        Role role
) {
}
