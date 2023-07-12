package com.krizan.social_media.controller.response;

import lombok.Builder;

@Builder
public record AppUserResponse(
    Long id,
    String role,
    String username,
    String email,
    String bio,
    Integer numberOfPosts,
    String avatarUrl,
    Integer numberOfFollowers,
    Integer numberOfFollowing,
    Boolean followedByCurrentUser
) {
}
