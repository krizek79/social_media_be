package com.krizan.social_media.repository;

import com.krizan.social_media.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findAppUserByEmail(String email);
    Optional<AppUser> findAppUserByUsernameOrEmail(String username, String email);
    Page<AppUser> findAllByUsernameContainingIgnoreCase(Pageable pageable, String username);
}
