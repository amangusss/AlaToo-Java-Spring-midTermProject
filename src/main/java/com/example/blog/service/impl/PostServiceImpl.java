package com.example.blog.service.impl;

import com.example.blog.dto.PostRequest;
import com.example.blog.dto.PostResponse;
import com.example.blog.entity.Post;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.mapper.PostMapper;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
    }

    public ResponseEntity<PostResponse> createPost(Long userId, PostRequest postRequest) {
        Post post = postMapper.toEntity(postRequest);
        post.setUsers(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found")));
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        Post createdPost = postRepository.save(post);
        return ResponseEntity.ok(postMapper.toDTO(createdPost));
    }

    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(postMapper::toDTO)
                .toList();
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElse(null);
        return post != null ? postMapper.toDTO(post) : null;
    }

    public PostResponse update(Long userId, Long id, PostRequest postRequest) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        existingPost.setBody(postRequest.getBody());
        existingPost.setTitle(postRequest.getTitle());
        existingPost.setUpdatedAt(LocalDateTime.now());
        Post updatedPost = postRepository.save(existingPost);

        return ResponseEntity.ok(postMapper.toDTO(updatedPost)).getBody();
    }

    public void deletePost(Long userId, Long id) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        }
    }
}
