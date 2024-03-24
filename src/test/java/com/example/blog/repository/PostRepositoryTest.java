package com.example.blog.repository;

import com.example.blog.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void testSavePost() {
        Post post = new Post();
        post.setTitle("Test Post");
        post.setBody("This is a test post.");

        Post savedPost = postRepository.save(post);

        assertEquals(post.getTitle(), savedPost.getTitle());
        assertEquals(post.getBody(), savedPost.getBody());
    }
}