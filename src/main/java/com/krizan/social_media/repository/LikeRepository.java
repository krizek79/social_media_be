package com.krizan.social_media.repository;

import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Like;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findLikeByPostIdAndAppUser(Long postId, AppUser appUser);
    Optional<Like> findLikeByCommentIdAndAppUser(Long commentId, AppUser appUser);
}
