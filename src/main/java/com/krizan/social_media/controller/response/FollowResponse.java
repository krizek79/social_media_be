package com.krizan.social_media.controller.response;

import com.krizan.social_media.model.Follow;
import lombok.Getter;

@Getter
public class FollowResponse {

    private final Long id;

    public FollowResponse(Follow follow) {
        this.id = follow.getId();
    }
}
