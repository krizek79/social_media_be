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
    @Query(
        """
            SELECT u FROM AppUser u
            WHERE u <> :currentUser AND u NOT IN
                (SELECT f.followed FROM Follow f WHERE f.follower = :currentUser)
            ORDER BY u.id
        """
    )
    Page<AppUser> findAppUsersNotFollowedByUser(Pageable pageable, AppUser currentUser);

}
