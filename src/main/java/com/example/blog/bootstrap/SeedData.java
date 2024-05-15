package com.example.blog.bootstrap;

import com.example.blog.entity.Post;
import com.example.blog.entity.Users;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SeedData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public SeedData(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void run(String... args) {
        Users user1 = new Users();
        user1.setUsername("david.ostapchenko");
        user1.setPassword("Parol123");
        user1.setFirstName("David");
        user1.setLastName("Ostapchenko");
        userRepository.save(user1);

        Users user2 = new Users();
        user2.setUsername("azamat.chambulov");
        user2.setPassword("Potnoelico");
        user2.setFirstName("Azamat");
        user2.setLastName("Chambulov");
        userRepository.save(user2);

        Post post1 = new Post();
        post1.setTitle("CS:2");
        post1.setBody("This is bullshit!");
        post1.setUsers(user1);
        post1.setCreatedAt(LocalDateTime.now());
        post1.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setTitle("Dota 2");
        post2.setBody("I'm an orphan");
        post2.setUsers(user2);
        post2.setCreatedAt(LocalDateTime.now());
        post2.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post2);
    }
}