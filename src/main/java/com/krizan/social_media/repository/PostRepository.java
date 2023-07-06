package com.krizan.social_media.repository;

import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findPostsByOwnerOrderByCreatedAtDesc(Pageable pageable, AppUser appUser);
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
