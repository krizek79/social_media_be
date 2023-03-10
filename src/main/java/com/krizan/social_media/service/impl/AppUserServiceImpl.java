package com.krizan.social_media.service.impl;

import com.krizan.social_media.controller.exception.IllegalOperationException;
import com.krizan.social_media.controller.exception.NotFoundException;
import com.krizan.social_media.controller.exception.UnsatisfyingParameterException;
import com.krizan.social_media.controller.request.RegistrationRequest;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Role;
import com.krizan.social_media.repository.AppUserRepository;
import com.krizan.social_media.service.api.AppUserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Slf4j
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
    public AppUser getAppUserByEmail(String email) {
        return appUserRepository.findAppUserByEmail(email).orElseThrow(
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
            () -> new NotFoundException("User not found")
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
            throw new UnsatisfyingParameterException("Email cannot be null");
        if (!validateEmail(request.email()))
            throw new UnsatisfyingParameterException("Email is not valid");
        if (request.email().isEmpty())
            throw new UnsatisfyingParameterException("Email cannot be empty");

        Optional<AppUser> appUserByEmail = appUserRepository.findAppUserByEmail(request.email());
        if (appUserByEmail.isPresent()) {
            throw new IllegalOperationException("User with this email already exists");
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
                "https://ui-avatars.com/api/?name=" + request.username() + "&background=random"
            )
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

    private Boolean validateEmail(String email) {
        String regex = "^[^\\s@]{3,}@[^\\s@]+\\.[^\\s@]{2,4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
