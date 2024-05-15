package com.example.blog.controller;

import com.example.blog.dto.PostRequest;
import com.example.blog.dto.PostResponse;
import com.example.blog.entity.Post;
import com.example.blog.entity.Users;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.mapper.PostMapper;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private Post post;
    private PostRequest postRequest;
    private PostResponse postResponse;
    private Users user;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setId(1L);
        post.setTitle("Test Post");
        post.setBody("This is a test post body");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRequest = new PostRequest();
        postRequest.setTitle("Test Post");
        postRequest.setBody("This is a test post body");

        postResponse = new PostResponse();
        postResponse.setId(1L);
        postResponse.setTitle("Test Post");
        postResponse.setBody("This is a test post body");
        postResponse.setCreatedAt(LocalDateTime.now());
        postResponse.setUpdatedAt(LocalDateTime.now());

        user = new Users();
        user.setId(1L);
        user.setUsername("test@example");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
    }

    @Test
    void createPost() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postMapper.toEntity(any(PostRequest.class))).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postMapper.toDTO(any(Post.class))).thenReturn(postResponse);

        ResponseEntity<PostResponse> response = postService.createPost(1L, postRequest);

        assertNotNull(response);
        assertEquals(ResponseEntity.ok(postResponse).getBody(), response.getBody());
        verify(userRepository, times(1)).findById(1L);
        verify(postMapper, times(1)).toEntity(postRequest);
        verify(postRepository, times(1)).save(post);
        verify(postMapper, times(1)).toDTO(post);
    }

    @Test
    void getAllPosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(post);

        List<PostResponse> postResponses = new ArrayList<>();
        postResponses.add(postResponse);

        when(postRepository.findAll()).thenReturn(posts);
        when(postMapper.toDTO(any(Post.class))).thenReturn(postResponse);

        List<PostResponse> result = postService.getAllPosts();

        assertNotNull(result);
        assertEquals(postResponses, result);
        verify(postRepository, times(1)).findAll();
        verify(postMapper, times(1)).toDTO(post);
    }

    @Test
    void getPostById() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postMapper.toDTO(any(Post.class))).thenReturn(postResponse);

        PostResponse result = postService.getPostById(1L);

        assertNotNull(result);
        assertEquals(postResponse, result);
        verify(postRepository, times(1)).findById(1L);
        verify(postMapper, times(1)).toDTO(post);
    }

    @Test
    void deletePost() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.existsById(1L)).thenReturn(true);
        doNothing().when(postRepository).deleteById(1L);

        assertDoesNotThrow(() -> postService.deletePost(1L, 1L));
        verify(userRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).existsById(1L);
        verify(postRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletePost_PostNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.existsById(1L)).thenReturn(false);

        assertDoesNotThrow(() -> postService.deletePost(1L, 1L));
        verify(userRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).existsById(1L);
        verify(postRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void update_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.update(1L, 1L, postRequest));
        verify(userRepository, times(1)).findById(1L);
        verify(postRepository, times(0)).findById(anyLong());
        verify(postRepository, times(0)).save(any(Post.class));
    }

    @Test
    void update_PostNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.update(1L, 1L, postRequest));
        verify(userRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(0)).save(any(Post.class));
    }
}
