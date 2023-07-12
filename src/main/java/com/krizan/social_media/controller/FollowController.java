package com.krizan.social_media.controller;

import com.krizan.social_media.controller.response.FollowResponse;
import com.krizan.social_media.model.mapper.Mapper;
import com.krizan.social_media.service.api.AppUserService;
import com.krizan.social_media.service.api.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowController {

    private final FollowService followService;
    private final AppUserService appUserService;
    private final Mapper mapper;

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public FollowResponse follow(@PathVariable("id") Long id) {
        log.info(
            appUserService.getCurrentAppUser().getUsername()
                + ": POST - follow (id: " + id + ")"
        );
        return mapper.mapFollowToResponse(followService.follow(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public FollowResponse unfollow(@PathVariable("id") Long id) {
        log.info(
            appUserService.getCurrentAppUser().getUsername()
                + ": POST - unfollow (id: " + id + ")"
        );
        return mapper.mapFollowToResponse(followService.unfollow(id));
    }
}
