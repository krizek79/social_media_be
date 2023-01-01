package com.krizan.social_media.controller.response;

import com.krizan.social_media.model.Post;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostResponse {

    private final Long id;
    private final String ownerUsername;
    private final String body;
    private final LocalDateTime createdAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.ownerUsername = post.getOwner().getUsername();
        this.body = post.getBody();
        this.createdAt = post.getCreatedAt();
    }
}
