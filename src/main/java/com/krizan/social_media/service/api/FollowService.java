package com.krizan.social_media.service.api;

import com.krizan.social_media.model.Follow;

public interface FollowService {

    Follow follow(Long followedId);
    Follow unfollow(Long followedId);
}
