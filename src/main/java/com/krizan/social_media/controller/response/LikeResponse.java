package com.krizan.social_media.controller.response;

import com.krizan.social_media.model.Like;
import lombok.Getter;

@Getter
public class LikeResponse {

    private final Long id;
    private final String avatarUrl;
    private final String username;
    private final Long postId;
    private final Long commentId;

    public LikeResponse(Like like) {
        this.id = like.getId();
        this.username = like.getAppUser().getUsername();
        this.avatarUrl = like.getAppUser().getAvatarUrl();
        if (like.getPost() != null) {
            this.postId = like.getPost().getId();
        } else {
            this.postId = null;
        }
        if (like.getComment() != null) {
            this.commentId = like.getComment().getId();
        } else {
            this.commentId = null;
        }
    }
}
