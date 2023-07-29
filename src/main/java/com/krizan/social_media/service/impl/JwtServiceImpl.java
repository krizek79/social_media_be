package com.krizan.social_media.service.impl;

import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.service.api.AppUserService;
import com.krizan.social_media.service.api.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtEncoder jwtEncoder;
    private final AppUserService appUserService;
    @Value("${security.jwtExpirationTimeInMillis}")
    private Long jwtExpirationTimeInMillis;

    @Override
    public String generateTokenByAuthentication(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        AppUser appUser = appUserService.getAppUserByEmail(principal.getUsername());
        return generateTokenByAppUser(appUser);
    }

    @Override
    public String generateTokenByAppUser(AppUser appUser) {
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusMillis(jwtExpirationTimeInMillis))
            .subject(appUser.getEmail())
            .claim("role", appUser.getRole())
            .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    @Override
    public long getJwtExpirationTimeInMillis() {
        return this.jwtExpirationTimeInMillis;
    }

}
