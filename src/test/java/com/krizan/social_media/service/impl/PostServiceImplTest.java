package com.krizan.social_media.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.krizan.social_media.controller.exception.NotFoundException;
import com.krizan.social_media.controller.exception.UnsatisfyingParameterException;
import com.krizan.social_media.controller.request.PostCreationRequest;
import com.krizan.social_media.controller.request.PostUpdateRequest;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Post;
import com.krizan.social_media.model.Role;
import com.krizan.social_media.repository.PostRepository;
import com.krizan.social_media.service.api.AppUserService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private AppUserService appUserService;

    @BeforeEach
    void setUp() {
        postService = new PostServiceImpl(postRepository, appUserService);
    }

    @Test
    void testGetAllPosts() {
        // Setup
        Post post1 = Post.builder()
            .owner(new AppUser())
            .body("Post 1")
            .build();
        Post post2 = Post.builder()
            .owner(new AppUser())
            .body("Post 2")
            .build();
        List<Post> posts = Arrays.asList(post1, post2);

        when(postRepository.findAll()).thenReturn(posts);

        // Execute
        List<Post> result = postService.getAllPosts();

        // Verify
        verify(postRepository).findAll();
        assertEquals(posts, result);
    }

    @Test
    void testGetPostById() {
        // Setup
        Post post = Post.builder()
            .owner(new AppUser())
            .body("Post 1")
            .build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        // Execute
        Post result = postService.getPostById(1L);

        // Verify
        verify(postRepository).findById(1L);
        assertEquals(post, result);
    }

    @Test
    void testGetPostByIdNotFoundException() {
        // Setup
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        // Verify
        assertThrows(NotFoundException.class, () -> postService.getPostById(1L));
    }

    @Test
    void testCreatePost() {
        // Arrange
        AppUser appUser = AppUser.builder()
            .role(Role.USER)
            .build();
        PostCreationRequest request = new PostCreationRequest("Test post");
        when(appUserService.getCurrentAppUser()).thenReturn(appUser);
        when(postRepository.save(any(Post.class))).thenAnswer(
            invocation -> invocation.getArgument(0));

        // when
        Post post = postService.createPost(request);

        // then
        Assertions.assertNotNull(post);
        Assertions.assertEquals(appUser, post.getOwner());
        Assertions.assertEquals(request.body(), post.getBody());
    }

    @Test
    void testCreatePostUnsatisfyingParameterException() {
        // Setup
        AppUser appUser = new AppUser();
        PostCreationRequest request = new PostCreationRequest("");

        when(appUserService.getCurrentAppUser()).thenReturn(appUser);

        // Verify
        assertThrows(UnsatisfyingParameterException.class,
            () -> postService.createPost(request));
    }

    @Test
    void testUpdatePost() {
        AppUser appUser = AppUser.builder()
            .role(Role.USER)
            .build();

        Post post = Post.builder()
            .id(1L)
            .body("Old post body.")
            .owner(appUser)
            .build();

        PostUpdateRequest request = new PostUpdateRequest("Updated post body");

        when(postRepository.findById(eq(post.getId()))).thenReturn(Optional.of(post));
        when(appUserService.getCurrentAppUser()).thenReturn(appUser);
        when(postRepository.save(any(Post.class))).thenAnswer(
            invocation -> invocation.getArgument(0));

        Post updatedPost = postService.updatePost(post.getId(), request);

        assertEquals(1L, updatedPost.getId());
        assertEquals("Updated post body", updatedPost.getBody());
        assertEquals(appUser, updatedPost.getOwner());
    }

    @Test
    void deletePost() {
        AppUser owner = AppUser.builder()
            .role(Role.USER)
            .build();

        Post post = Post.builder()
            .id(1L)
            .body("Post body.")
            .owner(owner)
            .build();
        when(appUserService.getCurrentAppUser()).thenReturn(owner);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        postService.deletePost(1L);

        verify(appUserService, times(1)).getCurrentAppUser();
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).delete(post);
    }
}
