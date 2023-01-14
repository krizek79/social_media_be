package com.krizan.social_media.configuration;

import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.service.api.AppUserService;
import java.time.Instant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtEncoder jwtEncoder;
    private final AppUserService appUserService;
    @Getter
    @Value("${security.jwtExpirationTimeInMillis}")
    private Long jwtExpirationTimeInMillis;

    public String generateTokenByAuthentication(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        AppUser appUser = appUserService.getAppUserByUsername(principal.getUsername());
        return generateTokenByAppUser(appUser);
    }

    public String generateTokenByAppUser(AppUser appUser) {
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusMillis(jwtExpirationTimeInMillis))
            .subject(appUser.getUsername())
            .claim("role", appUser.getRole())
            .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
