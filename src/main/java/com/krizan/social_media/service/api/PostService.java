package com.krizan.social_media.service.api;

import com.krizan.social_media.controller.request.PostCreationRequest;
import com.krizan.social_media.controller.request.PostUpdateRequest;
import com.krizan.social_media.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Page<Post> getAllPosts(Pageable pageable);
    Page<Post> getAllPostsByUsername(Pageable pageable, String username);
    Post getPostById(Long id);
    Post createPost(PostCreationRequest request);
    Post updatePost(Long id, PostUpdateRequest request);
    void deletePost(Long id);
}
