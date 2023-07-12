package com.krizan.social_media.controller.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PostResponse(
    Long id,
    AppUserResponse author,
    String body,
    LocalDateTime createdAt,
    Integer numberOfComments,
    Integer numberOfLikes,
    Boolean likedByCurrentUser
) {
}
