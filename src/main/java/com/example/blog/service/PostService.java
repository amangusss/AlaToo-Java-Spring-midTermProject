package com.example.blog.service;

import com.example.blog.dto.PostRequest;
import com.example.blog.dto.PostResponse;
import com.example.blog.entity.Post;
import com.example.blog.mapper.PostMapper;
import com.example.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public PostResponse createPost(PostResponse postResponse) {
        Post postEntity = postMapper.toEntity(postResponse);
        postEntity.setCreatedAt(LocalDateTime.now());
        postEntity.setUpdatedAt(LocalDateTime.now());
        Post savedPost = postRepository.save(postEntity);
        return postMapper.toModel(savedPost);
    }

    public List<PostResponse> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(postMapper::toModel)
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElse(null);
        return post != null ? postMapper.toModel(post) : null;
    }

    public PostResponse update(Long id, PostResponse postResponse) {
        Post existingPost = postRepository.findById(id)
                .orElse(null);

        if (existingPost != null) {
            Post updatedPost = postMapper.toEntity(postResponse);
            updatedPost.setId(id);
            updatedPost.setCreatedAt(existingPost.getCreatedAt());
            updatedPost.setUpdatedAt(LocalDateTime.now());
            return postMapper.toModel(postRepository.save(updatedPost));
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
