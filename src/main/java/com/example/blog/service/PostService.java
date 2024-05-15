package com.example.blog.service;

import com.example.blog.dto.PostRequest;
import com.example.blog.dto.PostResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {

    ResponseEntity<PostResponse> createPost(Long userId, PostRequest postRequest);

    List<PostResponse> getAllPosts();

    PostResponse getPostById(Long id);

    PostResponse update(Long userId, Long id, PostRequest postRequest);

    void deletePost(Long userId, Long id);
}
