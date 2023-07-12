package com.krizan.social_media.controller.response;

import lombok.Builder;

@Builder
public record LikeResponse(
    Long id,
    Long appUserId,
    Long postId,
    Long commentId
) {
}
