package com.krizan.social_media.controller.response;

import com.krizan.social_media.model.AppUser;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class AppUserResponse {

    private final Long id;
    private final String username;
    private final String avatarUrl;
    private final List<PostResponse> posts = new ArrayList<>();

    public AppUserResponse(AppUser appUser) {
        this.id = appUser.getId();
        this.username = appUser.getUsername();
        this.avatarUrl = appUser.getAvatarUrl();
        this.posts.addAll(appUser.getPosts()
            .stream()
            .map(PostResponse::new)
            .toList()
        );
    }
}
