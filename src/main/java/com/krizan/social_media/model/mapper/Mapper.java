package com.krizan.social_media.model.mapper;

import com.krizan.social_media.controller.response.AppUserResponse;
import com.krizan.social_media.controller.response.CommentResponse;
import com.krizan.social_media.controller.response.FollowResponse;
import com.krizan.social_media.controller.response.LikeResponse;
import com.krizan.social_media.controller.response.PostResponse;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Comment;
import com.krizan.social_media.model.Follow;
import com.krizan.social_media.model.Like;
import com.krizan.social_media.model.Post;
import com.krizan.social_media.service.api.AppUserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mapper {

    private final AppUserService appUserService;

    public AppUserResponse mapAppUserToResponse(AppUser appUser) {
        return AppUserResponse.builder()
            .id(appUser.getId())
            .username(appUser.getUsername())
            .role(appUser.getRole().name())
            .email(appUser.getEmail())
            .bio(appUser.getBio())
            .numberOfPosts(appUser.getPosts().size())
            .avatarUrl(appUser.getAvatarUrl())
            .numberOfFollowers(appUser.getFollowers().size())
            .numberOfFollowing(appUser.getFollowing().size())
            .followedByCurrentUser(isFollowedByCurrentUser(appUser))
            .build();
    }

    public PostResponse mapPostToResponse(Post post) {
        return PostResponse.builder()
            .id(post.getId())
            .author(mapAppUserToResponse(post.getOwner()))
            .createdAt(post.getCreatedAt())
            .body(post.getBody())
            .numberOfLikes(post.getLikes().size())
            .numberOfComments(post.getComments().size())
            .likedByCurrentUser(isPostLikedByCurrentUser(post))
            .build();
    }

    public CommentResponse mapCommentToResponse(Comment comment) {
        return CommentResponse.builder()
            .id(comment.getId())
            .postId(comment.getPost().getId())
            .author(mapAppUserToResponse(comment.getAuthor()))
            .body(comment.getBody())
            .createdAt(comment.getCreatedAt())
            .parentCommentId(
                comment.getParentComment() != null ? comment.getParentComment().getId() : null
            )
            .childComments(mapChildComments(comment.getChildComments()))
            .numberOfLikes(comment.getLikes().size())
            .likedByCurrentUser(isCommentLikedByCurrentUser(comment))
            .build();
    }

    private List<CommentResponse> mapChildComments(List<Comment> childComments) {
        return childComments.stream()
            .map(this::mapCommentToResponse)
            .collect(Collectors.toList());
    }

    public FollowResponse mapFollowToResponse(Follow follow) {
        return FollowResponse.builder()
            .id(follow.getId())
            .followerId(follow.getFollower().getId())
            .followedId(follow.getFollowed().getId())
            .build();
    }

    public LikeResponse mapLikeToResponse(Like like) {
        return LikeResponse.builder()
            .id(like.getId())
            .appUserId(like.getAppUser().getId())
            .postId(
                like.getPost() != null ? like.getPost().getId() : null
            )
            .commentId(
                like.getComment() != null ? like.getComment().getId() : null
            )
            .build();
    }

    private Boolean isFollowedByCurrentUser(AppUser appUser) {
        AppUser currentUser = appUserService.getCurrentAppUser();
        List<Follow> followersList = appUser.getFollowers();
        for (Follow follow : followersList) {
            if (follow.getFollower().equals(currentUser)) {
                return true;
            }
        }
        return false;
    }

    private Boolean isCommentLikedByCurrentUser(Comment comment) {
        AppUser currentUser = appUserService.getCurrentAppUser();
        List<Like> likes = comment.getLikes();
        for (Like like : likes) {
            if (like.getAppUser().equals(currentUser)) {
                return true;
            }
        }
        return false;
    }

    private Boolean isPostLikedByCurrentUser(Post post) {
        AppUser currentUser = appUserService.getCurrentAppUser();
        List<Like> likes = post.getLikes();
        for (Like like : likes) {
            if (like.getAppUser().equals(currentUser)) {
                return true;
            }
        }
        return false;
    }
}
