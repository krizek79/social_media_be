package com.krizan.social_media.service.api;

import com.krizan.social_media.controller.request.RegistrationRequest;
import com.krizan.social_media.controller.request.UpdateAppUserRequest;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface AppUserService extends UserDetailsService {

    AppUser getCurrentAppUser();
    AppUser getAppUserById(Long id);
    AppUser getAppUserByUsername(String username);
    AppUser getAppUserByEmail(String email);
    Optional<AppUser> getAppUserByUsernameOrEmail(String usernameOrEmail);
    List<AppUser> getAllAppUsers();
    Page<AppUser> searchForAppUsersLikeUsername(Pageable pageable, String username);
    Page<AppUser> getUnfollowedAppUsers(Pageable pageable);
    Page<AppUser> getFollowersByUsername(Pageable pageable, String username);
    Page<AppUser> getFollowedByUsername(Pageable pageable, String username);
    AppUser createAppUser(RegistrationRequest request, Role role);
    AppUser updateAppUser(Long id, UpdateAppUserRequest request);
    void deleteAppUser(Long id);
}
