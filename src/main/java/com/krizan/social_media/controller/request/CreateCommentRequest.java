package com.krizan.social_media.controller.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateCommentRequest(
    Long postId,
    Long parentCommentId,
    @NotEmpty(message = "Body is mandatory.")
    String body
) {
}
