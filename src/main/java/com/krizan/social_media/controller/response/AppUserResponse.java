package com.krizan.social_media.controller.response;

import com.krizan.social_media.model.AppUser;
import lombok.Getter;

@Getter
public class AppUserResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final String bio;
    private final Integer numberOfPosts;
    private final String avatarUrl;

    public AppUserResponse(AppUser appUser) {
        this.id = appUser.getId();
        this.username = appUser.getUsername();
        this.email = appUser.getEmail();
        this.bio = appUser.getBio();
        this.numberOfPosts = appUser.getPosts().size();
        this.avatarUrl = appUser.getAvatarUrl();
    }
}
