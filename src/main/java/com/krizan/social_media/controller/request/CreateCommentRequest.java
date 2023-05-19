package com.krizan.social_media.controller.request;

public record CreateCommentRequest(
    Long postId,
    Long parentCommentId,
    String body
) {
}
