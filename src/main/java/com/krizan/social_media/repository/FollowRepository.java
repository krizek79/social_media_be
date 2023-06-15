package com.krizan.social_media.repository;

import com.krizan.social_media.model.Follow;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollower_IdAndFollowed_Id(Long followerId, Long followedId);
}
