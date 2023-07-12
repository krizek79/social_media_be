package com.krizan.social_media.controller.response;

import lombok.Builder;

@Builder
public record FollowResponse(
    Long id,
    Long followerId,
    Long followedId
) {
}
