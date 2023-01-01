package com.krizan.social_media.controller;

import com.krizan.social_media.service.api.AppUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app-user")
public record AppUserController(AppUserService appUserService) {

}
