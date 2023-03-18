package com.krizan.social_media.controller;

import com.krizan.social_media.controller.request.CreateAppUserRequest;
import com.krizan.social_media.controller.request.UpdateAppUserRequest;
import com.krizan.social_media.controller.response.AppUserResponse;
import com.krizan.social_media.service.api.AppUserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/app-users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public AppUserResponse getAppUserById(@PathVariable("id") Long id) {
        log.info(appUserService.getCurrentAppUser().getUsername()
            + ": GET - getAppUserById (id: " + id + ")"
        );
        return new AppUserResponse(appUserService.getAppUserById(id));
    }

    @GetMapping(params = "username")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public AppUserResponse getAppUserByUsername(@RequestParam String username) {
        log.info(appUserService.getCurrentAppUser().getUsername()
            + ": GET - getAppUserByUsername (username: " + username + ")"
        );
        return new AppUserResponse(appUserService.getAppUserByUsername(username));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<AppUserResponse> getAllAppUsers() {
        log.info(appUserService.getCurrentAppUser().getUsername() + ": GET - getAllAppUsers");
        return appUserService.getAllAppUsers()
            .stream()
            .map(AppUserResponse::new)
            .collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public AppUserResponse createAppUser(@RequestBody CreateAppUserRequest request) {
        log.info(appUserService.getCurrentAppUser().getUsername() + ": POST - createAppUser");
        return new AppUserResponse(
            appUserService.createAppUser(request.registrationRequest(), request.role())
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public AppUserResponse updateAppUser(
        @PathVariable Long id,
        @RequestBody UpdateAppUserRequest request
    ) {
        log.info(appUserService.getCurrentAppUser().getUsername() + ": PUT - updateAppUser");
        return new AppUserResponse(appUserService.updateAppUser(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAppUser(@PathVariable("id") Long id) {
        log.info(appUserService.getCurrentAppUser().getUsername() + ": DELETE - deleteAppUser");
        appUserService.deleteAppUser(id);
    }
}
