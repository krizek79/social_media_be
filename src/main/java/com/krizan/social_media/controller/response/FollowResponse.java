package com.krizan.social_media.controller.response;

import com.krizan.social_media.model.Follow;
import lombok.Getter;

@Getter
public class FollowResponse {

    private final Long id;
    private final SimpleFollowAppUserResponse follower;
    private final SimpleFollowAppUserResponse followed;

    public FollowResponse(Follow follow) {
        this.id = follow.getId();
        this.follower = new SimpleFollowAppUserResponse(follow.getFollower());
        this.followed = new SimpleFollowAppUserResponse(follow.getFollowed());
    }
}
