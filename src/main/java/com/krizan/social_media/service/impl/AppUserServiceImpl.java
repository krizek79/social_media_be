package com.krizan.social_media.service.impl;

import com.krizan.social_media.controller.exception.NotFoundException;
import com.krizan.social_media.controller.exception.UnsatisfyingParameterException;
import com.krizan.social_media.controller.request.RegistrationRequest;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Role;
import com.krizan.social_media.repository.AppUserRepository;
import com.krizan.social_media.service.api.AppUserService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder encoder;

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
    public String getAppUserUsernameByEmail(String email) {
        AppUser appUser = appUserRepository.findAppUserByEmail(email).orElseThrow(
            () -> new NotFoundException("nehehe")
        );
        return appUser.getUsername();
    }

    @Override
    public List<AppUser> getAllAppUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser createAppUser(RegistrationRequest request, Role role) {
        if (request.email() == null)
            throw new UnsatisfyingParameterException("Email cannot be null.");
        if (request.email().isEmpty())
            throw new UnsatisfyingParameterException("Email cannot be empty.");
        if (request.username() == null)
            throw new UnsatisfyingParameterException("Username cannot be null.");
        if (request.username().isEmpty())
            throw new UnsatisfyingParameterException("Username cannot be empty.");
        if (request.password() == null)
            throw new UnsatisfyingParameterException("Password cannot be null.");
        if (request.password().isEmpty())
            throw new UnsatisfyingParameterException("Password cannot be empty.");
        if (!request.password().equals(request.matchingPassword()))
            throw new UnsatisfyingParameterException("Passwords do not match.");

        AppUser appUser = AppUser.builder()
            .email(request.email())
            .username(request.username())
            .password(encoder.encode(request.password()))
            .role(role)
            .posts(new ArrayList<>())
            .locked(false)
            .enabled(true)
            .build();

        return appUserRepository.save(appUser);
    }

    @Override
    public void deleteAppUser(Long id) {
        AppUser appUser = getAppUserById(id);
        appUserRepository.delete(appUser);
    }
}
