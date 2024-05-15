package com.example.blog.mapper;

import com.example.blog.dto.PostRequest;
import com.example.blog.dto.PostResponse;
import com.example.blog.entity.Post;
import com.example.blog.entity.Users;
import com.example.blog.mapper.PostMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostMapperTest {

    private PostMapper postMapper;
    private Post post;
    private PostRequest postRequest;

    @BeforeEach
    public void setUp() {
        postMapper = Mappers.getMapper(PostMapper.class);
        post = new Post();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setBody("Test Body");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        Users user = new Users();
        user.setId(1L);
        post.setUsers(user);

        postRequest = new PostRequest();
        postRequest.setId(1L);
        postRequest.setTitle("Test Title");
        postRequest.setBody("Test Body");
    }

    @Test
    public void testToEntity() {
        Post mappedPost = postMapper.toEntity(postRequest);

        assertEquals(postRequest.getId(), mappedPost.getId());
        assertEquals(postRequest.getTitle(), mappedPost.getTitle());
        assertEquals(postRequest.getBody(), mappedPost.getBody());
    }
}
