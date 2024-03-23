package com.example.Blog.service;

import com.example.Blog.entity.Post;
import com.example.Blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post createPost(Post post) {
        if (post.getId() == 0) {
            post.setCreatedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());
        }
        return postRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post update(Long id, Post postDetails) {
        Post existingPost = postRepository.findById(id)
                .orElse(null);

        if (existingPost != null) {
            existingPost.setTitle(postDetails.getTitle());
            existingPost.setBody(postDetails.getBody());
            existingPost.setUpdatedAt(LocalDateTime.now());
            return postRepository.save(existingPost);
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
