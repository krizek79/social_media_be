package com.krizan.social_media.controller.response;

import com.krizan.social_media.model.AppUser;
import lombok.Getter;

@Getter
public class SimpleFollowAppUserResponse {

    private final String username;
    private final String avatarUrl;

    public SimpleFollowAppUserResponse(AppUser appUser) {
        this.username = appUser.getUsername();
        this.avatarUrl = appUser.getAvatarUrl();
    }
}
