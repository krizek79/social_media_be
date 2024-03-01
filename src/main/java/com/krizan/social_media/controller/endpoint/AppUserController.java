package com.krizan.social_media.controller.endpoint;

import com.krizan.social_media.controller.request.CreateAppUserRequest;
import com.krizan.social_media.controller.request.UpdateAppUserRequest;
import com.krizan.social_media.controller.response.AppUserResponse;
import com.krizan.social_media.model.mapper.Mapper;
import com.krizan.social_media.service.api.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("app-users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final Mapper mapper;

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public AppUserResponse getAppUserById(@PathVariable("id") Long id) {
        return mapper.mapAppUserToResponse(appUserService.getAppUserById(id));
    }

    @GetMapping("username/{username}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public AppUserResponse getAppUserByUsername(@PathVariable String username) {
        return mapper.mapAppUserToResponse(appUserService.getAppUserByUsername(username));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<AppUserResponse> getAllAppUsers() {
        return appUserService.getAllAppUsers()
            .stream()
            .map(mapper::mapAppUserToResponse)
            .collect(Collectors.toList());
    }

    @GetMapping("search")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<AppUserResponse> searchForAppUsersLikeUsername(
        @ParameterObject Pageable pageable,
        @RequestParam(required = false) String username
    ) {
        return appUserService.searchForAppUsersLikeUsername(pageable, username).getContent()
            .stream()
            .map(mapper::mapAppUserToResponse)
            .collect(Collectors.toList());
    }

    @GetMapping("unfollowed")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<AppUserResponse> getUnfollowedAppUsers(@ParameterObject Pageable pageable) {
        return appUserService.getUnfollowedAppUsers(pageable).getContent()
            .stream()
            .map(mapper::mapAppUserToResponse)
            .collect(Collectors.toList());
    }

    @GetMapping("/followers/{username}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<AppUserResponse> getFollowersByUsername(
        @ParameterObject Pageable pageable,
        @PathVariable String username
    ) {
        return appUserService.getFollowersByUsername(pageable, username).getContent()
            .stream()
            .map(mapper::mapAppUserToResponse)
            .collect(Collectors.toList());
    }

    @GetMapping("following/{username}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<AppUserResponse> getFollowedByUsername(
        @ParameterObject Pageable pageable,
        @PathVariable String username
    ) {
        return appUserService.getFollowedByUsername(pageable, username).getContent()
            .stream()
            .map(mapper::mapAppUserToResponse)
            .collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public AppUserResponse createAppUser(@RequestBody CreateAppUserRequest request) {
        return mapper.mapAppUserToResponse(
            appUserService.createAppUser(request.registrationRequest(), request.role())
        );
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public AppUserResponse updateAppUser(
        @PathVariable Long id,
        @RequestBody UpdateAppUserRequest request
    ) {
        return mapper.mapAppUserToResponse(appUserService.updateAppUser(id, request));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAppUser(@PathVariable("id") Long id) {
        appUserService.deleteAppUser(id);
    }
}
