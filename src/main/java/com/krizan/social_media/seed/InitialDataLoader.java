package com.krizan.social_media.seed;

import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Role;
import com.krizan.social_media.repository.AppUserRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public void run(String... args) {
        seedAppUserTable();
    }

    private void seedAppUserTable() {
        AppUser admin = AppUser.builder()
            .email("admin@admin.com")
            .username("admin")
            .password(encoder.encode("admin"))
            .posts(new ArrayList<>())
            .role(Role.ADMIN)
            .enabled(true)
            .locked(false)
            .build();
        appUserRepository.save(admin);
    }
}
