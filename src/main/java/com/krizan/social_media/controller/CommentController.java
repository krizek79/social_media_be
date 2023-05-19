package com.krizan.social_media.controller;

import com.krizan.social_media.controller.request.CreateCommentRequest;
import com.krizan.social_media.controller.request.UpdateCommentRequest;
import com.krizan.social_media.controller.response.CommentResponse;
import com.krizan.social_media.service.api.CommentService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/post/{postId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<CommentResponse> getAllCommentsByPostId(@PathVariable("postId") Long postId) {
        return commentService.getAllCommentsByPostId(postId)
            .stream()
            .map(CommentResponse::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CommentResponse getCommentById(@PathVariable("id") Long id) {
        return new CommentResponse(commentService.getCommentById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CommentResponse createComment(@RequestBody CreateCommentRequest request) {
        return new CommentResponse(commentService.createComment(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CommentResponse updateComment(
        @PathVariable("id") Long id,
        @RequestBody UpdateCommentRequest request
    ) {
        return new CommentResponse(commentService.updateComment(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
    }
}

