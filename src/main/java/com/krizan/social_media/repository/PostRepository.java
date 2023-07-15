package com.krizan.social_media.repository;

import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findPostsByAuthor(Pageable pageable, AppUser appUser);
    Page<Post> findPostsByAuthorInOrAuthor(Pageable pageable, List<AppUser> followedUsers, AppUser owner);
}
