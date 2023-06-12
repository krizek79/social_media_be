package com.krizan.social_media.service.api;

public interface FollowService {

    void follow(Long id);
    void unfollow(Long id);
}
