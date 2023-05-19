package com.krizan.social_media.controller.response;

import com.krizan.social_media.model.Comment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class CommentResponse {

    private final Long id;
    private final AppUserResponse author;
    private final Long postId;
    private final Long parentCommentId;
    private final List<CommentResponse> childComments;
    private final LocalDateTime createdAt;
    private final String body;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.author = new AppUserResponse(comment.getAuthor());
        this.postId = comment.getPost().getId();
        if (comment.getParentComment() != null) {
            this.parentCommentId = comment.getParentComment().getId();
        } else {
            this.parentCommentId = null;
        }
        this.childComments = comment.getChildComments()
            .stream()
            .map(CommentResponse::new)
            .collect(Collectors.toList());
        this.createdAt = comment.getCreatedAt();
        this.body = comment.getBody();
    }
}
