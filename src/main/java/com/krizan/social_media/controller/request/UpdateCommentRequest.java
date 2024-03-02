package com.krizan.social_media.controller.request;

import jakarta.validation.constraints.NotEmpty;

public record UpdateCommentRequest(@NotEmpty(
        message = "Body is mandatory.")
        String body
) {
}
