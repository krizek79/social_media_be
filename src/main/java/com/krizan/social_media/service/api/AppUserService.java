package com.krizan.social_media.service.api;

import com.krizan.social_media.controller.request.RegistrationRequest;
import com.krizan.social_media.controller.request.UpdateAppUserRequest;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Role;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    AppUser getCurrentAppUser();
    AppUser getAppUserById(Long id);
    AppUser getAppUserByEmail(String email);
    AppUser getAppUserByUsername(String username);
    AppUser getAppUserByUsernameOrEmail(String usernameOrEmail);
    List<AppUser> getAllAppUsers();
    AppUser createAppUser(RegistrationRequest request, Role role);
    AppUser updateAppUser(Long id, UpdateAppUserRequest request);
    void deleteAppUser(Long id);
}
