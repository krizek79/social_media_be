package com.krizan.social_media.controller.response;

import com.krizan.social_media.model.Follow;
import lombok.Getter;

@Getter
public class FollowResponse {

    private final Long id;
    private final String follower;
    private final String followed;

    public FollowResponse(Follow follow) {
        this.id = follow.getId();
        this.follower = follow.getFollower().getUsername();
        this.followed = follow.getFollowed().getUsername();
    }
}
