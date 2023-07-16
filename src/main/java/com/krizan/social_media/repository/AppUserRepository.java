package com.krizan.social_media.repository;

import com.krizan.social_media.model.AppUser;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findAppUserByEmail(String email);
    Optional<AppUser> findAppUserByUsernameOrEmail(String username, String email);
    Page<AppUser> findAllByUsernameContainingIgnoreCase(Pageable pageable, String username);
    @Query(value = """
        SELECT * FROM app_user
        WHERE id != :currentUserId
        AND id NOT IN (
            SELECT followed_id FROM follow
            WHERE follower_id = :currentUserId
        ) ORDER BY RAND()
        """, nativeQuery = true
    )
    Page<AppUser> getRandomUnfollowedAppUsers(Pageable pageable, Long currentUserId);
}
