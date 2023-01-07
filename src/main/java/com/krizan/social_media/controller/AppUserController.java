package com.krizan.social_media.controller;

import com.krizan.social_media.controller.request.CreateAppUserRequest;
import com.krizan.social_media.controller.response.AppUserResponse;
import com.krizan.social_media.service.api.AppUserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app-user")
public record AppUserController(AppUserService appUserService) {

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public AppUserResponse getAppUserById(@PathVariable("id") Long id) {
        return new AppUserResponse(appUserService.getAppUserById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<AppUserResponse> getAllAppUsers() {
        return appUserService.getAllAppUsers()
            .stream()
            .map(AppUserResponse::new)
            .collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public AppUserResponse createAppUser(@RequestBody CreateAppUserRequest request) {
        return new AppUserResponse(
            appUserService.createAppUser(request.registrationRequest(), request.role())
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAppUser(@PathVariable("id") Long id) {
        appUserService.deleteAppUser(id);
    }
}
