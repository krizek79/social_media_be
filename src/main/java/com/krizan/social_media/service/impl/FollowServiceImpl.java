package com.krizan.social_media.service.impl;

import com.krizan.social_media.controller.exception.IllegalOperationException;
import com.krizan.social_media.controller.exception.NotFoundException;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Follow;
import com.krizan.social_media.repository.FollowRepository;
import com.krizan.social_media.service.api.AppUserService;
import com.krizan.social_media.service.api.FollowService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final AppUserService appUserService;

    @Override
    public Follow follow(Long followedId) {
        AppUser currentUser = appUserService.getCurrentAppUser();
        AppUser followedUser = appUserService.getAppUserById(followedId);
        Optional<Follow> follow = followRepository.findByFollower_IdAndFollowed_Id(
            currentUser.getId(),
            followedUser.getId()
        );
        if (follow.isPresent()) {
            throw new IllegalOperationException(
                "AppUser with id: " + currentUser.getId() + " already follows AppUser with id: "
                    + followedUser.getId() + "...");
        }

        return followRepository.save(
            Follow.builder()
                .follower(currentUser)
                .followed(followedUser)
                .build()
        );
    }

    @Override
    public Follow unfollow(Long followedId) {
        AppUser currentUser = appUserService.getCurrentAppUser();
        AppUser followedUser = appUserService.getAppUserById(followedId);
        Follow follow = followRepository.findByFollower_IdAndFollowed_Id(
            currentUser.getId(),
            followedUser.getId()
        ).orElseThrow(() -> new NotFoundException(
            "AppUser with id: " + currentUser.getId() + " does not follow AppUser with id: "
                + followedUser.getId() + "..."
        ));

        followRepository.delete(follow);
        return follow;
    }
}
