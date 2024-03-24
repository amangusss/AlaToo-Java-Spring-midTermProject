package com.example.blog.mapper;

import com.example.blog.dto.PostRequest;
import com.example.blog.dto.PostResponse;
import com.example.blog.entity.Post;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostMapperTest {

    private final PostMapper postMapper = PostMapper.INSTANCE;

    @Test
    void testToModel() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Post");
        post.setBody("This is a test post.");

        PostResponse postResponse = postMapper.toModel(post);

        assertEquals(post.getId(), postResponse.getId());
        assertEquals(post.getTitle(), postResponse.getTitle());
        assertEquals(post.getBody(), postResponse.getBody());
    }

    @Test
    void testToEntity() {
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("Test Post");
        postRequest.setBody("This is a test post.");

        Post post = postMapper.toEntity(postRequest);

        assertEquals(postRequest.getTitle(), post.getTitle());
        assertEquals(postRequest.getBody(), post.getBody());
    }
}