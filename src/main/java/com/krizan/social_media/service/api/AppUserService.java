package com.krizan.social_media.service.api;

import com.krizan.social_media.controller.request.RegistrationRequest;
import com.krizan.social_media.controller.request.UpdateAppUserRequest;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    AppUser getCurrentAppUser();
    AppUser getAppUserById(Long id);
    AppUser getAppUserByUsername(String username);
    Optional<AppUser> getAppUserByUsernameOrEmail(String usernameOrEmail);
    List<AppUser> getAllAppUsers();
    Page<AppUser> searchForAppUsersLikeUsername(Pageable pageable, String username);
    Page<AppUser> getRandomUnfollowedAppUsers(Pageable pageable);
    AppUser createAppUser(RegistrationRequest request, Role role);
    AppUser updateAppUser(Long id, UpdateAppUserRequest request);
    void deleteAppUser(Long id);
}
