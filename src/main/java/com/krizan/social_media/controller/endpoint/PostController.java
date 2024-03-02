package com.krizan.social_media.controller.endpoint;

import com.krizan.social_media.controller.request.PostCreationRequest;
import com.krizan.social_media.controller.request.PostUpdateRequest;
import com.krizan.social_media.controller.response.PostResponse;
import com.krizan.social_media.model.mapper.Mapper;
import com.krizan.social_media.service.api.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final Mapper mapper;

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public PostResponse getPostById(@PathVariable Long id) {
        return mapper.mapPostToResponse(postService.getPostById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<PostResponse> getAllPosts(
        @ParameterObject Pageable pageable
    ) {
        return postService.getAllPosts(pageable).getContent()
            .stream()
            .map(mapper::mapPostToResponse)
            .collect(Collectors.toList());
    }

    @GetMapping("username/{username}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<PostResponse> getAllPostsByUsername(
        @ParameterObject Pageable pageable,
        @PathVariable("username") String username
    ) {
        return postService.getAllPostsByUsername(pageable, username).getContent()
            .stream()
            .map(mapper::mapPostToResponse)
            .collect(Collectors.toList());
    }

    @GetMapping("followed")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<PostResponse> getPostsOfFollowedUsers(
        @ParameterObject Pageable pageable
    ) {
        return postService.getPostsOfFollowedUsers(pageable).getContent()
            .stream()
            .map(mapper::mapPostToResponse)
            .collect(Collectors.toList());
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public PostResponse updatePost(@PathVariable Long id, @Valid @RequestBody PostUpdateRequest request) {
        return mapper.mapPostToResponse(postService.updatePost(id, request));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public PostResponse createPost(@Valid @RequestBody PostCreationRequest request) {
        return mapper.mapPostToResponse(postService.createPost(request));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
