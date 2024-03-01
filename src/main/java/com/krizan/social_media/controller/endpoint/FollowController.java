package com.krizan.social_media.controller.endpoint;

import com.krizan.social_media.controller.response.FollowResponse;
import com.krizan.social_media.model.mapper.Mapper;
import com.krizan.social_media.service.api.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("follows")
public class FollowController {

    private final FollowService followService;
    private final Mapper mapper;

    @PostMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public FollowResponse follow(@PathVariable("id") Long id) {
        return mapper.mapFollowToResponse(followService.follow(id));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public FollowResponse unfollow(@PathVariable("id") Long id) {
        return mapper.mapFollowToResponse(followService.unfollow(id));
    }
}
