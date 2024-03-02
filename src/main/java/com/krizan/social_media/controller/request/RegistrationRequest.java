package com.krizan.social_media.controller.request;

import com.krizan.social_media.validation.ValueMatching;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@ValueMatching(
        value = "password",
        matchingValue = "matchingPassword"
)
public record RegistrationRequest(
        @NotEmpty(message = "Email is mandatory.")
        @Email(message = "Email is not valid.")
        String email,
        @NotEmpty(message = "Username is mandatory.")
        String username,
        @NotEmpty(message = "Password is mandatory.")
        @Size(min = 4, message = "Password should have at least 4 characters")
        String password,
        @NotEmpty(message = "Matching password is mandatory.")
        String matchingPassword
) {
}
