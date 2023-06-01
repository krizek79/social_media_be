package com.krizan.social_media.repository;

import com.krizan.social_media.model.Comment;
import com.krizan.social_media.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostAndParentCommentIsNull(Post post);
}
