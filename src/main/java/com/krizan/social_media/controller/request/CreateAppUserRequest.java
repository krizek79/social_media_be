package com.krizan.social_media.controller.request;

import com.krizan.social_media.model.Role;

public record CreateAppUserRequest(
    RegistrationRequest registrationRequest,
    Role role
) {
}
