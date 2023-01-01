package com.krizan.social_media.service.api;

import com.krizan.social_media.controller.request.PostCreationRequest;
import com.krizan.social_media.controller.request.PostUpdateRequest;
import com.krizan.social_media.model.Post;
import java.util.List;

public interface PostService {

    List<Post> getAllPosts();
    Post getPostById(Long id);
    Post createPost(PostCreationRequest request);
    Post updatePost(Long id, PostUpdateRequest request);
    void deletePost(Long id);
}
