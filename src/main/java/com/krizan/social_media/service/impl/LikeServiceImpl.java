package com.krizan.social_media.service.impl;

import com.krizan.social_media.controller.exception.IllegalOperationException;
import com.krizan.social_media.controller.exception.NotFoundException;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Comment;
import com.krizan.social_media.model.Like;
import com.krizan.social_media.model.Post;
import com.krizan.social_media.repository.LikeRepository;
import com.krizan.social_media.service.api.AppUserService;
import com.krizan.social_media.service.api.CommentService;
import com.krizan.social_media.service.api.LikeService;
import com.krizan.social_media.service.api.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final AppUserService appUserService;
    private final PostService postService;
    private final CommentService commentService;

    @Override
    public Like likePost(Long id) {
        AppUser currentUser = appUserService.getCurrentAppUser();
        Post post = postService.getPostById(id);
        Optional<Like> likeByPostIdAndAppUser = likeRepository.findLikeByPostIdAndAppUser(
            id,
            currentUser
        );
        if (likeByPostIdAndAppUser.isPresent()) {
            throw new IllegalOperationException(
                "Post with id: " + id + " is already liked by user: " + currentUser.getEmail()
                    + "...");
        }

        Like like = Like.builder()
            .appUser(currentUser)
            .post(post)
            .comment(null)
            .build();

        return likeRepository.save(like);
    }

    @Override
    public Like likeComment(Long id) {
        AppUser currentUser = appUserService.getCurrentAppUser();
        Comment comment = commentService.getCommentById(id);
        Optional<Like> likeByCommentIdAndAppUser = likeRepository.findLikeByCommentIdAndAppUser(
            id,
            currentUser
        );
        if (likeByCommentIdAndAppUser.isPresent()) {
            throw new IllegalOperationException(
                "Comment with id: " + id + " is already liked by user: " + currentUser.getEmail()
                    + "...");
        }

        Like like = Like.builder()
            .appUser(currentUser)
            .post(null)
            .comment(comment)
            .build();

        return likeRepository.save(like);
    }

    @Override
    public Like unlikePost(Long id) {
        AppUser currentUser = appUserService.getCurrentAppUser();
        Like likeByPostIdAndAppUser = likeRepository.findLikeByPostIdAndAppUser(
            id,
            currentUser
        ).orElseThrow(() -> new NotFoundException(
            "Like from user: " + currentUser.getEmail()
                + " on post with id: " + id + " does not exist..."));

        likeRepository.delete(likeByPostIdAndAppUser);

        return likeByPostIdAndAppUser;
    }

    @Override
    public Like unlikeComment(Long id) {
        AppUser currentUser = appUserService.getCurrentAppUser();
        Like likeByCommentIdAndAppUser = likeRepository.findLikeByCommentIdAndAppUser(
            id,
            currentUser
        ).orElseThrow(() -> new NotFoundException(
            "Like from user: " + currentUser.getEmail()
                + " on comment with id: " + id + " does not exist..."));

        likeRepository.delete(likeByCommentIdAndAppUser);

        return likeByCommentIdAndAppUser;
    }
}
