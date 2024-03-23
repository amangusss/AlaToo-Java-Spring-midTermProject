package com.example.blog.service;

import com.example.blog.dto.PostDTO;
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

    public PostDTO createPost(PostDTO postDTO) {
        Post postEntity = postMapper.postDTOToPost(postDTO);
        postEntity.setCreatedAt(LocalDateTime.now());
        postEntity.setUpdatedAt(LocalDateTime.now());
        Post savedPost = postRepository.save(postEntity);
        return postMapper.postToPostDTO(savedPost);
    }

    public List<PostDTO> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(postMapper::postToPostDTO)
                .collect(Collectors.toList());
    }

    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElse(null);
        return post != null ? postMapper.postToPostDTO(post) : null;
    }

    public PostDTO update(Long id, PostDTO postDTO) {
        Post existingPost = postRepository.findById(id)
                .orElse(null);

        if (existingPost != null) {
            Post updatedPost = postMapper.postDTOToPost(postDTO);
            updatedPost.setId(id);
            updatedPost.setCreatedAt(existingPost.getCreatedAt());
            updatedPost.setUpdatedAt(LocalDateTime.now());
            return postMapper.postToPostDTO(postRepository.save(updatedPost));
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
