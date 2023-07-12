package com.krizan.social_media.controller.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record CommentResponse(
    Long id,
    AppUserResponse author,
    Long postId,
    Long parentCommentId,
    List<CommentResponse> childComments,
    LocalDateTime createdAt,
    String body,
    Integer numberOfLikes,
    Boolean likedByCurrentUser
) {
}
