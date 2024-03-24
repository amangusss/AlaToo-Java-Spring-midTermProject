package com.example.blog.controller;

import com.example.blog.dto.PostResponse;
import com.example.blog.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @Test
    void testGetAllPosts() {
        List<PostResponse> posts = Collections.singletonList(new PostResponse());
        when(postService.getAllPosts()).thenReturn(posts);

        ResponseEntity<List<PostResponse>> responseEntity = postController.getAllPosts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(posts, responseEntity.getBody());
    }
}