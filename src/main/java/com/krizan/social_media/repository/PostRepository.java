package com.krizan.social_media.repository;

import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findPostsByOwner(AppUser appUser);
}
