package com.krizan.social_media.service.api;

import com.krizan.social_media.model.AppUser;
import org.springframework.security.core.Authentication;

public interface JwtService {

    String generateTokenByAuthentication(Authentication authentication);
    String generateTokenByAppUser(AppUser appUser);
    long getJwtExpirationTimeInMillis();
}
