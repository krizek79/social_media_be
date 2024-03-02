package com.krizan.social_media.service.impl;

import com.krizan.social_media.controller.exception.IllegalOperationException;
import com.krizan.social_media.controller.exception.NotFoundException;
import com.krizan.social_media.controller.exception.UnsatisfyingParameterException;
import com.krizan.social_media.controller.request.RegistrationRequest;
import com.krizan.social_media.controller.request.UpdateAppUserRequest;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Role;
import com.krizan.social_media.repository.AppUserRepository;
import com.krizan.social_media.service.api.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = getAppUserByEmail(email);
        return new User(
            appUser.getEmail(),
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = null;
        if (principal instanceof Jwt) {
            email = ((Jwt) principal).getSubject();
        }
        if (principal instanceof User) {
            email = ((User) principal).getUsername();
        }
        return getAppUserByEmail(email);
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
    public AppUser getAppUserByEmail(String email) {
        return appUserRepository.findAppUserByEmail(email).orElseThrow(
                () -> new NotFoundException("User not found")
        );
    }

    @Override
    public Optional<AppUser> getAppUserByUsernameOrEmail(String usernameOrEmail) {
        return appUserRepository.findAppUserByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    }

    @Override
    public List<AppUser> getAllAppUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public Page<AppUser> searchForAppUsersLikeUsername(Pageable pageable, String username) {
        return appUserRepository.findAllByUsernameContainingIgnoreCase(pageable, username);
    }

    @Override
    public Page<AppUser> getUnfollowedAppUsers(Pageable pageable) {
        AppUser currentUser = getCurrentAppUser();
        return appUserRepository.findAppUsersNotFollowedByUser(pageable, currentUser);
    }

    @Override
    public Page<AppUser> getFollowersByUsername(Pageable pageable, String username) {
        AppUser appUser = getAppUserByUsername(username);
        return appUserRepository.findFollowersByFollowed(pageable, appUser);
    }

    @Override
    public Page<AppUser> getFollowedByUsername(Pageable pageable, String username) {
        AppUser appUser = getAppUserByUsername(username);
        return appUserRepository.findFollowedByFollower(pageable, appUser);
    }

    @Override
    public AppUser createAppUser(RegistrationRequest request, Role role) {
        if (request.email() == null)
            throw new UnsatisfyingParameterException("Email cannot be null");
        if (!validateEmail(request.email()))
            throw new UnsatisfyingParameterException("Email is not valid");
        if (request.email().isEmpty())
            throw new UnsatisfyingParameterException("Email cannot be empty");

        Optional<AppUser> appUserByEmail = appUserRepository.findAppUserByEmail(request.email());
        if (appUserByEmail.isPresent()) {
            throw new IllegalOperationException("User with this username or email already exists");
        }

        if (request.username() == null)
            throw new UnsatisfyingParameterException("Username cannot be null");
        if (request.username().isEmpty())
            throw new UnsatisfyingParameterException("Username cannot be empty");

        Optional<AppUser> appUserByUsername = appUserRepository.findByUsername(request.username());
        if (appUserByUsername.isPresent()) {
            throw new IllegalOperationException("This username is already taken");
        }

        if (request.password() == null)
            throw new UnsatisfyingParameterException("Password cannot be null");
        if (request.password().isEmpty())
            throw new UnsatisfyingParameterException("Password cannot be empty");
        if (!request.password().equals(request.matchingPassword()))
            throw new UnsatisfyingParameterException("Passwords do not match");

        AppUser appUser = AppUser.builder()
            .email(request.email())
            .username(request.username())
            .password(encoder.encode(request.password()))
            .role(role)
            .avatarUrl(
                "https://ui-avatars.com/api/?name="
                    + request.username()
                    + "&background=random&size=256"
            )
            .locked(false)
            .enabled(true)
            .build();

        return appUserRepository.save(appUser);
    }

    @Override
    public AppUser updateAppUser(Long id, UpdateAppUserRequest request) {
        AppUser appUser = getAppUserById(id);

        if (request.avatarUrl() != null && !request.avatarUrl().isEmpty()) {
            appUser.setAvatarUrl(request.avatarUrl());
        } else {
            appUser.setAvatarUrl(
                "https://ui-avatars.com/api/?name="
                + appUser.getUsername()
                + "&background=random&size=256"
            );
        }
        if (request.bio() != null) {
            appUser.setBio(request.bio());
        }

        return appUserRepository.save(appUser);
    }

    @Override
    public void deleteAppUser(Long id) {
        AppUser appUser = getAppUserById(id);
        appUserRepository.delete(appUser);
    }

    private Boolean validateEmail(String email) {
        String regex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
