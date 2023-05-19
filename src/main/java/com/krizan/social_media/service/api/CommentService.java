package com.krizan.social_media.service.api;

import com.krizan.social_media.controller.request.CreateCommentRequest;
import com.krizan.social_media.controller.request.UpdateCommentRequest;
import com.krizan.social_media.model.Comment;
import java.util.List;

public interface CommentService {

    List<Comment> getAllCommentsByPostId(Long postId);
    Comment getCommentById(Long commentId);
    Comment createComment(CreateCommentRequest request);
    Comment updateComment(Long commentId, UpdateCommentRequest request);
    void deleteComment(Long commentId);
}
