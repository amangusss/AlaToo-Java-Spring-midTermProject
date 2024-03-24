package com.example.blog.service;

import com.example.blog.dto.PostRequest;
import com.example.blog.dto.PostResponse;
import com.example.blog.entity.Post;
import com.example.blog.mapper.PostMapper;
import com.example.blog.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public ResponseEntity<PostResponse> createPost(PostRequest postRequest) {
        Post post = postMapper.toEntity(postRequest);
        Post createdPost = postRepository.save(post);
        PostResponse postResponse = postMapper.toModel(createdPost);
        post.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok(postResponse);
    }

    public List<PostResponse> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(postMapper::toModel)
                .toList();
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElse(null);
        return post != null ? postMapper.toModel(post) : null;
    }

    public PostResponse update(Long id, PostRequest postRequest) {
        Post existingPost = postRepository.findById(id)
                .orElse(null);

        if (existingPost != null) {
            Post post = postMapper.toEntity(postRequest);
            Post updatedPost = postRepository.save(post);
            PostResponse postResponse = postMapper.toModel(updatedPost);
            updatedPost.setId(id);
            updatedPost.setCreatedAt(existingPost.getCreatedAt());
            updatedPost.setUpdatedAt(LocalDateTime.now());
            return postResponse;
        }

        return null;
    }

    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }

        return false;
    }

}
