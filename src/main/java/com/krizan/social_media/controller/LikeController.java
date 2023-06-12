package com.krizan.social_media.controller;

import com.krizan.social_media.controller.response.LikeResponse;
import com.krizan.social_media.service.api.AppUserService;
import com.krizan.social_media.service.api.LikeService;
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
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;
    private final AppUserService appUserService;

    @PostMapping("/post/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public LikeResponse likePost(@PathVariable("id") Long id) {
        log.info(
            appUserService.getCurrentAppUser().getUsername()
                + ": POST - likePost (id: " + id + ")"
        );
        return new LikeResponse(likeService.likePost(id));
    }

    @PostMapping("/comment/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public LikeResponse likeComment(@PathVariable("id") Long id) {
        log.info(
            appUserService.getCurrentAppUser().getUsername()
                + ": GET - likeComment (id: " + id + ")"
        );
        return new LikeResponse(likeService.likeComment(id));
    }

    @DeleteMapping("/post/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public LikeResponse unlikePost(@PathVariable("id") Long id) {
        log.info(
            appUserService.getCurrentAppUser().getUsername()
                + ": GET - unlikePost (id: " + id + ")"
        );
        return new LikeResponse(likeService.unlikePost(id));
    }

    @DeleteMapping("/comment/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public LikeResponse unlikeComment(@PathVariable("id") Long id) {
        log.info(
            appUserService.getCurrentAppUser().getUsername()
                + ": GET - unlikeComment (id: " + id + ")"
        );
        return new LikeResponse(likeService.unlikeComment(id));
    }
}
