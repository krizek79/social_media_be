package com.krizan.social_media.controller;

import com.krizan.social_media.controller.request.PostCreationRequest;
import com.krizan.social_media.controller.request.PostUpdateRequest;
import com.krizan.social_media.controller.response.PostResponse;
import com.krizan.social_media.service.api.AppUserService;
import com.krizan.social_media.service.api.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final AppUserService appUserService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public PostResponse getPostById(@PathVariable Long id) {
        log.info(
            appUserService.getCurrentAppUser().getUsername()
                + ": GET - getPostById (id: " + id + ")"
        );
        return new PostResponse(postService.getPostById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<PostResponse> getAllPosts() {
        log.info(appUserService.getCurrentAppUser().getUsername() + ": GET - getAllPosts");
        return postService.getAllPosts()
            .stream()
            .map(PostResponse::new)
            .toList();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public PostResponse updatePost(@PathVariable Long id, @RequestBody PostUpdateRequest request) {
        log.info(appUserService.getCurrentAppUser().getUsername()
            + ": PATCH - updatePost (id: " + id + ")"
        );
        return new PostResponse(postService.updatePost(id, request));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public PostResponse createPost(@RequestBody PostCreationRequest request) {
        log.info(appUserService.getCurrentAppUser().getUsername() + ": POST - createPost");
        return new PostResponse(postService.createPost(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void deletePost(@PathVariable Long id) {
        log.info(appUserService.getCurrentAppUser().getUsername()
            + ": DELETE - deletePost (id: " + id + ")"
        );
        postService.deletePost(id);
    }
}
