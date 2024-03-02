package com.krizan.social_media.configuration;

import com.krizan.social_media.controller.exception.NotFoundException;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Post;
import com.krizan.social_media.model.Role;
import com.krizan.social_media.repository.AppUserRepository;
import com.krizan.social_media.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomCommandLineRunner implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PostRepository postRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${server.port}")
    private String serverPort;
    @Value("${springdoc.swagger-ui.path}")
    private String swaggerPath;

    @Value("${https.enabled}")
    private Boolean httpsEnabled;

    private static final String ERROR_USER_NOT_FOUND = "User not found";

    private static final String OPENAPI_MESSAGE = "Link to the OpenApi documentation: %s";

    @Override
    public void run(String... args) {
        printOpenApiUrl();
        seedAppUserTable();
        seedPostTable();
    }

    private void printOpenApiUrl() {
        String serverHost = InetAddress.getLoopbackAddress().getHostName();
        String protocol = httpsEnabled ? "https" : "http";
        String swaggerUrl = protocol + "://" + serverHost + ":" + serverPort + swaggerPath;
        log.info(OPENAPI_MESSAGE.formatted(swaggerUrl));
    }

    private void seedAppUserTable() {
        AppUser admin = AppUser.builder()
            .email("admin@admin.com")
            .username("admin")
            .password(encoder.encode("admin"))
            .avatarUrl("https://ui-avatars.com/api/?name=admin&background=random&size=256")
            .bio("👑")
            .role(Role.ADMIN)
            .enabled(true)
            .locked(false)
            .build();
        appUserRepository.save(admin);

        AppUser user1 = AppUser.builder()
            .email("krizan.matej79@gmail.com")
            .username("Matej Križan")
            .password(encoder.encode("pass"))
            .bio("We Choose Truth Over Facts! 🤑")
            .avatarUrl("https://ui-avatars.com/api/?name=Matej%20Križan&background=random&size=256")
            .role(Role.USER)
            .enabled(true)
            .locked(false)
            .build();
        appUserRepository.save(user1);

        AppUser user2 = AppUser.builder()
            .email("test1@test.com")
            .username("Gregor Nehehe")
            .password(encoder.encode("pass"))
            .bio(
                """
                    Enigmatic cosmic dreamer, whispering to stars, sipping stardust tea, \
                    and dancing with parallel realities. Collector of oddities, \
                    maker of cryptic art, and eternal seeker of the universe's hidden truths.
                """
            )
            .avatarUrl(
                "https://ui-avatars.com/api/?name=Gregor%20Nehehe&background=random&size=256"
            )
            .role(Role.USER)
            .enabled(true)
            .locked(false)
            .build();
        appUserRepository.save(user2);

        AppUser user3 = AppUser.builder()
            .email("test2@test.com")
            .username("Suzan Rodriguez")
            .password(encoder.encode("pass"))
            .bio(
                """
                    Tech wizard \uD83D\uDE80 | AI enthusiast \uD83E\uDD16 | Coffee addict ☕️ | \
                    Constantly coding \uD83D\uDCBB | Embracing the future \uD83C\uDF0C | #TechNinja
                """
            )
            .avatarUrl(
                "https://ui-avatars.com/api/?name=Suzan%20Rodriguez&background=random&size=256"
            )
            .role(Role.USER)
            .enabled(true)
            .locked(false)
            .build();
        appUserRepository.save(user3);

        AppUser user4 = AppUser.builder()
            .email("test3@test.com")
            .username("Michael Sort")
            .password(encoder.encode("pass"))
            .bio(
                """
                    Adventure seeker \uD83C\uDF0D | Nature lover \uD83C\uDF3F \
                    | Aspiring photographer \uD83D\uDCF7 | Yoga enthusiast \uD83E\uDDD8\u200D♀ \
                    | Free spirit \uD83E\uDD8B | Wanderlust soul ✈️
                """
            )
            .avatarUrl("https://ui-avatars.com/api/?name=Michael%20Sort&background=random&size=256")
            .role(Role.USER)
            .enabled(true)
            .locked(false)
            .build();
        appUserRepository.save(user4);

        AppUser user5 = AppUser.builder()
            .email("test4@test.com")
            .username("Emily Blunt")
            .password(encoder.encode("pass"))
            .bio(
                """
                    Bookworm \uD83D\uDCDA | Tea connoisseur \uD83C\uDF75 | Writer at heart ✍️ \
                    | Dreamer with a pen \uD83C\uDF0C | Empowering through words \uD83D\uDCD6 \
                    | Spreading positivity ✨
                """
            )
            .avatarUrl("https://ui-avatars.com/api/?name=Emily%20Blunt&background=random&size=256")
            .role(Role.USER)
            .enabled(true)
            .locked(false)
            .build();
        appUserRepository.save(user5);

        AppUser user6 = AppUser.builder()
            .email("test5@test.com")
            .username("Liam Johnson")
            .password(encoder.encode("pass"))
            .bio(
                """
                    Sports fanatic ⚽️ | Fitness junkie \uD83D\uDCAA | Dog lover \uD83D\uDC36 \
                    | Entrepreneurial spirit \uD83D\uDCC8 | Inspired by challenges \uD83C\uDFC6 \
                    | Making dreams happen \uD83C\uDF1F
                """
            )
            .avatarUrl("https://ui-avatars.com/api/?name=Liam%20Johnson&background=random&size=256")
            .role(Role.USER)
            .enabled(true)
            .locked(false)
            .build();
        appUserRepository.save(user6);

        AppUser user7 = AppUser.builder()
            .email("test6@test.com")
            .username("Noah Ramirez")
            .password(encoder.encode("pass"))
            .bio(
                """
                    Music maestro \uD83C\uDFB6 | Multi-instrumentalist \uD83C\uDFB8 \
                    | Melody creator \uD83C\uDFB5 | Passionate about rhythm \uD83D\uDD25 \
                    | Harmonizing souls ❤️ | Chasing musical dreams \uD83C\uDF1F
                """
            )
            .avatarUrl("https://ui-avatars.com/api/?name=Noah%20Ramirez&background=random&size=256")
            .role(Role.USER)
            .enabled(true)
            .locked(false)
            .build();
        appUserRepository.save(user7);

        AppUser user8 = AppUser.builder()
            .email("test7@test.com")
            .username("Chloe Lee")
            .password(encoder.encode("pass"))
            .bio(
                """
                    Art aficionado \uD83C\uDFA8 | Painter by passion \uD83D\uDD8C️ \
                    | Capturing beauty in colors \uD83C\uDF08 \
                    | Inspiring through visuals \uD83C\uDF1F \
                    | Brushstrokes of emotion \uD83C\uDFAD \
                    | Creating a world on canvas \uD83C\uDF0C
                """
            )
            .avatarUrl("https://ui-avatars.com/api/?name=Chloe%20Lee&background=random&size=256")
            .role(Role.USER)
            .enabled(true)
            .locked(false)
            .build();
        appUserRepository.save(user8);

        AppUser user9 = AppUser.builder()
            .email("test8@test.com")
            .username("Oliver Wilson")
            .password(encoder.encode("pass"))
            .bio(
                """
                    Foodie at heart \uD83C\uDF54 | Culinary adventurer \uD83C\uDF2E \
                    | Amateur chef \uD83D\uDC68\u200D\uD83C\uDF73 \
                    | Mastering flavors \uD83C\uDF36 \
                    | Kitchen experiments \uD83D\uDD2C \
                    | Celebrating taste sensations \uD83C\uDF89
                """
            )
            .avatarUrl(
                "https://ui-avatars.com/api/?name=Oliver%20Wilson&background=random&size=256"
            )
            .role(Role.USER)
            .enabled(true)
            .locked(false)
            .build();
        appUserRepository.save(user9);
    }

    private void seedPostTable() {
        AppUser admin = appUserRepository.findAppUserByEmail("admin@admin.com")
            .orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

        List<Post> posts = new ArrayList<>();

        Post post1 = Post.builder()
            .author(admin)
            .body(
                """
                    My wife said I should do lunges to stay in shape. \
                    That would be a big step forward.
                """
            )
            .createdAt(LocalDateTime.now())
            .build();
        posts.add(post1);

        Post post2 = Post.builder()
            .author(admin)
            .body(
                """
                    Why do seagulls fly over the ocean? Because if they flew over the bay, \
                    we'd call them bagels.
                """
            )
            .createdAt(LocalDateTime.now())
            .build();
        posts.add(post2);

        Post post3 = Post.builder()
            .author(admin)
            .body(
                """
                    A skeleton walks into a bar and says, 'Hey, bartender. \
                    I'll have one beer and a mop.
                """
            )
            .createdAt(LocalDateTime.now())
            .build();
        posts.add(post3);

        Post post4 = Post.builder()
            .author(admin)
            .body(
                """
                    My wife is really mad at the fact that I have no sense of direction. \
                    So I packed up my stuff and right!
                """
            )
            .createdAt(LocalDateTime.now())
            .build();
        posts.add(post4);

        AppUser user1 = appUserRepository.findAppUserByEmail("krizan.matej79@gmail.com")
            .orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

        Post post5 = Post.builder()
            .author(user1)
            .body(
                """
                    What did the snail who was riding on the turtle's back say? Wheeeee!
                """
            )
            .createdAt(LocalDateTime.now())
            .build();
        posts.add(post5);

        Post post6 = Post.builder()
            .author(user1)
            .body(
                """
                    What does a pig put on dry skin? Oinkment.
                """
            )
            .createdAt(LocalDateTime.now())
            .build();
        posts.add(post6);

        AppUser user2 = appUserRepository.findAppUserByEmail("test1@test.com")
            .orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

        Post post7 = Post.builder()
            .author(user2)
            .body(
                """
                    My new favorite song!
                    https://www.youtube.com/watch?v=ZWU56UybP8Q&list=RDMMZWU56UybP8Q&start_radio=1
                """
            )
            .createdAt(LocalDateTime.now())
            .build();
        posts.add(post7);

        Post post8 = Post.builder()
            .author(user2)
            .body(
                """
                    🍔 Lunchtime cravings? Drop your favorite burger joint below, \
                    and let's find the best one in town! 🍟
                """
            )
            .createdAt(LocalDateTime.now())
            .build();
        posts.add(post8);

        Post post9 = Post.builder()
            .author(user2)
            .body(
                """
                    Gratitude can turn any day into a great day! \
                    Let's take a moment to share something we're thankful for today.
                    Tag a friend and spread the positivity! 🙏✨
                """
            )
            .createdAt(LocalDateTime.now())
            .build();
        posts.add(post9);

        AppUser user3 = appUserRepository.findAppUserByEmail("test2@test.com")
            .orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

        Post post10 = Post.builder()
            .author(user3)
            .body(
                """
                    Nature never fails to amaze me! Share your most breathtaking nature photos, \
                    and let's celebrate the wonders of our world.
                """
            )
            .createdAt(LocalDateTime.now())
            .build();
        posts.add(post10);

        AppUser user4 = appUserRepository.findAppUserByEmail("test3@test.com")
            .orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

        Post post11 = Post.builder()
            .author(user4)
            .body(
                """
                    Fitness is not just about hitting the gym; it's a lifestyle. \
                    Share your go-to workout routine and inspire others to get moving! 💪
                """
            )
            .createdAt(LocalDateTime.now())
            .build();
        posts.add(post11);

        postRepository.saveAll(posts);
    }
}
