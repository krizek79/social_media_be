package com.krizan.social_media.service.impl;

import com.krizan.social_media.repository.FollowRepository;
import com.krizan.social_media.service.api.AppUserService;
import com.krizan.social_media.service.api.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final AppUserService appUserService;

    @Override
    public void follow(Long id) {

    }

    @Override
    public void unfollow(Long id) {

    }
}
