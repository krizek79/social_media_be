package com.krizan.social_media.seed;

import com.krizan.social_media.controller.exception.NotFoundException;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Post;
import com.krizan.social_media.model.Role;
import com.krizan.social_media.repository.AppUserRepository;
import com.krizan.social_media.repository.PostRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PostRepository postRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public void run(String... args) {
        seedAppUserTable();
        seedPostTable();
    }

    private void seedAppUserTable() {
        AppUser admin = AppUser.builder()
            .email("admin@admin.com")
            .username("admin")
            .password(encoder.encode("admin"))
            .avatarUrl("https://ui-avatars.com/api/?name=admin&background=random")
            .posts(new ArrayList<>())
            .role(Role.ADMIN)
            .enabled(true)
            .locked(false)
            .build();
        appUserRepository.save(admin);

        AppUser user = AppUser.builder()
            .email("krizan.matej79@gmail.com")
            .username("Matej Križan")
            .password(encoder.encode("pass"))
            .avatarUrl("https://ui-avatars.com/api/?name=Matej%20Križan&background=random")
            .posts(new ArrayList<>())
            .role(Role.USER)
            .enabled(true)
            .locked(false)
            .build();
        appUserRepository.save(user);
    }

    private void seedPostTable() {
        AppUser appUser = appUserRepository.findAppUserByEmail("admin@admin.com")
            .orElseThrow(() -> new NotFoundException("User not found"));

        List<Post> posts = new ArrayList<>();

        Post post1 = Post.builder()
            .owner(appUser)
            .body(""
                + "My wife said I should do lunges to stay in shape. "
                + "That would be a big step forward."
            )
            .createdAt(LocalDateTime.now())
            .build();
        posts.add(post1);

        Post post2 = Post.builder()
            .owner(appUser)
            .body(""
                + "Why do seagulls fly over the ocean? Because if they flew over the bay, "
                + "we'd call them bagels."
            )
            .createdAt(LocalDateTime.now())
            .build();
        posts.add(post2);

        Post post3 = Post.builder()
            .owner(appUser)
            .body(""
                + "A skeleton walks into a bar and says, 'Hey, bartender. "
                + "I'll have one beer and a mop."
            )
            .createdAt(LocalDateTime.now())
            .build();
        posts.add(post3);

        Post post4 = Post.builder()
            .owner(appUser)
            .body(""
                + "My wife is really mad at the fact that I have no sense of direction. "
                + "So I packed up my stuff and right!"
            )
            .createdAt(LocalDateTime.now())
            .build();
        posts.add(post4);

        postRepository.saveAll(posts);
    }
}
