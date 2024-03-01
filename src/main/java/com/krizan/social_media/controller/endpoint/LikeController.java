package com.krizan.social_media.controller.endpoint;

import com.krizan.social_media.controller.response.LikeResponse;
import com.krizan.social_media.model.mapper.Mapper;
import com.krizan.social_media.service.api.AppUserService;
import com.krizan.social_media.service.api.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("likes")
public class LikeController {

    private final LikeService likeService;
    private final AppUserService appUserService;
    private final Mapper mapper;

    @PostMapping("post/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public LikeResponse likePost(@PathVariable("id") Long id) {
        return mapper.mapLikeToResponse(likeService.likePost(id));
    }

    @PostMapping("comment/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public LikeResponse likeComment(@PathVariable("id") Long id) {
        return mapper.mapLikeToResponse(likeService.likeComment(id));
    }

    @DeleteMapping("post/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public LikeResponse unlikePost(@PathVariable("id") Long id) {
        return mapper.mapLikeToResponse(likeService.unlikePost(id));
    }

    @DeleteMapping("comment/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public LikeResponse unlikeComment(@PathVariable("id") Long id) {
        return mapper.mapLikeToResponse(likeService.unlikeComment(id));
    }
}
