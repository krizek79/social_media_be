package com.krizan.social_media.service.impl;

import com.krizan.social_media.controller.exception.ForbiddenException;
import com.krizan.social_media.controller.exception.NotFoundException;
import com.krizan.social_media.controller.exception.UnsatisfyingParameterException;
import com.krizan.social_media.controller.request.PostCreationRequest;
import com.krizan.social_media.controller.request.PostUpdateRequest;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Follow;
import com.krizan.social_media.model.Post;
import com.krizan.social_media.model.Role;
import com.krizan.social_media.repository.PostRepository;
import com.krizan.social_media.service.api.AppUserService;
import com.krizan.social_media.service.api.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final AppUserService appUserService;

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> getAllPostsByUsername(Pageable pageable, String username) {
        AppUser owner = appUserService.getAppUserByUsername(username);
        return postRepository.findPostsByOwner(pageable, owner);
    }

    @Override
    public Page<Post> getPostsOfFollowedUsers(Pageable pageable) {
        AppUser currentUser = appUserService.getCurrentAppUser();
        List<Follow> followedUsers = currentUser.getFollowing();
        List<AppUser> users = followedUsers.stream()
            .map(Follow::getFollowed)
            .collect(Collectors.toList());
        return postRepository.findPostsByOwnerInOrOwner(pageable, users, currentUser);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Post with id: " + id + " does not exist"));
    }

    @Override
    public Post createPost(PostCreationRequest request) {
        AppUser appUser = appUserService.getCurrentAppUser();

        if (request.body() == null || request.body().isEmpty()) {
            throw new UnsatisfyingParameterException("Body cannot be empty");
        }

        Post post = Post.builder()
            .owner(appUser)
            .body(request.body())
            .comments(new ArrayList<>())
            .likes(new ArrayList<>())
            .build();

        appUser.getPosts().add(post);

        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, PostUpdateRequest request) {
        Post post = getPostById(id);
        AppUser appUser = appUserService.getCurrentAppUser();
        if (appUser != post.getOwner() && !appUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("You don't have permission to update this post.");
        }
        if (request.body() != null && !request.body().isEmpty()) {
            post.setBody(request.body());
        }

        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        Post post = getPostById(id);
        AppUser appUser = appUserService.getCurrentAppUser();
        if (appUser != post.getOwner() && !appUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("You don't have permission to delete this post.");
        }
        postRepository.delete(post);
    }
}

