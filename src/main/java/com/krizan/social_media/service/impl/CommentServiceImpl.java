package com.krizan.social_media.service.impl;

import com.krizan.social_media.controller.exception.ForbiddenException;
import com.krizan.social_media.controller.exception.NotFoundException;
import com.krizan.social_media.controller.request.CreateCommentRequest;
import com.krizan.social_media.controller.request.UpdateCommentRequest;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Comment;
import com.krizan.social_media.model.Post;
import com.krizan.social_media.model.Role;
import com.krizan.social_media.repository.CommentRepository;
import com.krizan.social_media.service.api.AppUserService;
import com.krizan.social_media.service.api.CommentService;
import com.krizan.social_media.service.api.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AppUserService appUserService;
    private final PostService postService;

    private final String ERROR_PERMISSION = "You don't have permission to modify this comment.";
    private final String ERROR_NOT_FOUND = "Comment with id: %s does not exist...";

    @Override
    public List<Comment> getAllCommentsByPostId(Long postId) {
        Post post = postService.getPostById(postId);
        return commentRepository.findAllByPostAndParentCommentIsNull(post);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
            () -> new NotFoundException(ERROR_NOT_FOUND.formatted(commentId))
        );
    }

    @Override
    public Comment createComment(CreateCommentRequest request) {
        AppUser author = appUserService.getCurrentAppUser();

        Post post = postService.getPostById(request.postId());
        Comment parentComment;
        if (request.parentCommentId() != null) {
            parentComment = commentRepository.findById(request.parentCommentId())
                .orElse(null);
        } else {
            parentComment = null;
        }

        Comment comment = Comment.builder()
            .author(author)
            .post(post)
            .parentComment(parentComment)
            .body(request.body())
            .build();

        if (parentComment != null) {
            parentComment.getChildComments().add(comment);
        }

        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long commentId, UpdateCommentRequest request) {
        Comment comment = getCommentById(commentId);
        AppUser appUser = appUserService.getCurrentAppUser();
        if (appUser != comment.getAuthor() && !appUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException(ERROR_PERMISSION);
        }

        comment.setBody(request.body());

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = getCommentById(commentId);
        AppUser appUser = appUserService.getCurrentAppUser();
        if (appUser != comment.getAuthor() && !appUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException(ERROR_PERMISSION);
        }
        commentRepository.delete(comment);
    }
}
