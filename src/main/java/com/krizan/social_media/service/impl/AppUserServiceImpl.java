package com.krizan.social_media.service.impl;

import com.krizan.social_media.controller.exception.NotFoundException;
import com.krizan.social_media.controller.request.RegistrationRequest;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Role;
import com.krizan.social_media.repository.AppUserRepository;
import com.krizan.social_media.service.api.AppUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = getAppUserByUsername(username);
        return new User(
            appUser.getUsername(),
            appUser.getPassword(),
            appUser.isEnabled(),
            appUser.isAccountNonExpired(),
            appUser.isCredentialsNonExpired(),
            appUser.isAccountNonLocked(),
            appUser.getAuthorities()
        );
    }

    @Override
    public AppUser getCurrentAppUser() {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getAppUserByUsername(principal.getSubject());
    }

    @Override
    public AppUser getAppUserById(Long id) {
        return appUserRepository.findById(id).orElseThrow(
            () -> new NotFoundException("User not found")
        );
    }

    @Override
    public AppUser getAppUserByUsername(String username) {
        return appUserRepository.findByUsername(username).orElseThrow(
            () -> new NotFoundException("User not found")
        );
    }

    @Override
    public List<AppUser> getAllAppUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser createAppUser(RegistrationRequest request, Role role) {
        return null;
    }

    @Override
    public void deleteAppUser(Long id) {
        AppUser appUser = getAppUserById(id);
        appUserRepository.delete(appUser);
    }
}
