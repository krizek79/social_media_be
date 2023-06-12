package com.krizan.social_media.controller.response;

import com.krizan.social_media.model.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PostResponse {

    private final Long id;
    private final AppUserResponse owner;
    private final String body;
    private final LocalDateTime createdAt;
    private final Integer numberOfComments;
    private final List<LikeResponse> likes;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.owner = new AppUserResponse(post.getOwner());
        this.body = post.getBody();
        this.createdAt = post.getCreatedAt();
        this.numberOfComments = post.getComments().size();
        this.likes = post.getLikes().stream()
            .map(LikeResponse::new)
            .collect(Collectors.toList());
    }
}
