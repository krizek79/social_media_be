package com.krizan.social_media.controller.response;

import com.krizan.social_media.model.AppUser;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class AppUserResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final String bio;
    private final Integer numberOfPosts;
    private final String avatarUrl;
    private final List<FollowResponse> followers;
    private final List<FollowResponse> following;

    public AppUserResponse(AppUser appUser) {
        this.id = appUser.getId();
        this.username = appUser.getUsername();
        this.email = appUser.getEmail();
        this.bio = appUser.getBio();
        this.numberOfPosts = appUser.getPosts().size();
        this.avatarUrl = appUser.getAvatarUrl();
        this.followers = appUser.getFollowers().stream()
            .map(FollowResponse::new)
            .collect(Collectors.toList());
        this.following = appUser.getFollowing().stream()
            .map(FollowResponse::new)
            .collect(Collectors.toList());
    }
}
