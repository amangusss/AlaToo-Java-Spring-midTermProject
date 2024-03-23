package com.example.Blog.config;

import com.example.Blog.entity.Post;
import com.example.Blog.entity.User;
import com.example.Blog.service.PostService;
import com.example.Blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = postService.getAll();

        if (posts.isEmpty()) {

            User user1 = new User();
            User user2 = new User();

            user1.setFirstName("account");
            user1.setLastName("account");
            user1.setEmail("account.user@gmail.com");
            user1.setPassword("password");

            user2.setFirstName("admin");
            user2.setLastName("admin");
            user2.setEmail("admin@gmail.com");
            user2.setPassword("password");

            userService.createUser(user1);
            userService.createUser(user2);

            Post post1 = new Post();
            post1.setTitle("title of post 1");
            post1.setBody("This is the body of post 1");
            post1.setUser(user1);

            Post post2 = new Post();
            post2.setTitle("title of post 2");
            post2.setBody("This is the body of post 2");
            post2.setUser(user2);

            postService.createPost(post1);
            postService.createPost(post2);
        }
    }
}
