package com.example.blog.bootstrap;

import com.example.blog.entity.Users;
import com.example.blog.entity.Post;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class SeedData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    public SeedData(UserRepository userRepository, PostRepository postRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        Users user1 = new Users();
        user1.setEmail("david.ostapchenko@alatoo.edu.kg");
        user1.setFirstName("David");
        user1.setLastName("Ostapchenko");
        user1.setPassword(passwordEncoder.encode("Parol123!"));
        userRepository.save(user1);

        Users user2 = new Users();
        user1.setEmail("azamat.chambulov@alatoo.edu.kg");
        user2.setFirstName("Azamat");
        user2.setLastName("Chambulov");
        user2.setPassword(passwordEncoder.encode("PotniyUrod"));
        userRepository.save(user2);

        Post post1 = new Post();
        post1.setTitle("Favorite Activity");
        post1.setBody("I love to play Dota 2 and being autistic");
        post1.setUsers(user1);
        post1.setCreatedAt(LocalDateTime.now());
        post1.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setTitle("Favorite Activity");
        post2.setBody("I also like to play Dota 2 and being homosexual");
        post2.setUsers(user2);
        post2.setCreatedAt(LocalDateTime.now());
        post2.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post2);
    }
}
