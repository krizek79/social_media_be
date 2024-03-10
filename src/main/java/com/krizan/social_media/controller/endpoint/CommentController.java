package com.krizan.social_media.controller.endpoint;

import com.krizan.social_media.controller.request.CreateCommentRequest;
import com.krizan.social_media.controller.request.UpdateCommentRequest;
import com.krizan.social_media.controller.response.CommentResponse;
import com.krizan.social_media.model.mapper.Mapper;
import com.krizan.social_media.service.api.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments")
public class CommentController {

    private final CommentService commentService;
    private final Mapper mapper;

    @GetMapping("post/{postId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<CommentResponse> getAllCommentsByPostId(@PathVariable("postId") Long postId) {
        return commentService.getAllCommentsByPostId(postId)
            .stream()
            .map(mapper::mapCommentToResponse)
            .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CommentResponse getCommentById(@PathVariable("id") Long id) {
        return mapper.mapCommentToResponse(commentService.getCommentById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CommentResponse createComment(@Valid @RequestBody CreateCommentRequest request) {
        return mapper.mapCommentToResponse(commentService.createComment(request));
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CommentResponse updateComment(
        @PathVariable("id") Long id,
        @Valid @RequestBody UpdateCommentRequest request
    ) {
        return mapper.mapCommentToResponse(commentService.updateComment(id, request));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
    }
}

