package com.example.blog.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import com.example.blog.entity.Post;
import com.example.blog.dto.PostDTO;
import com.example.blog.mapper.PostMapper;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private PostMapper postMapper;
    @Mock
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePost() {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Test Post");
        postDTO.setBody("Test Content");

        Post post = new Post();
        post.setTitle("Test Post");
        post.setBody("Test Content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        when(postMapper.postDTOToPost(postDTO)).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postMapper.postToPostDTO(post)).thenReturn(postDTO);

        PostDTO createdPostDTO = postService.createPost(postDTO);

        assertNotNull(createdPostDTO);
        assertEquals(postDTO.getTitle(), createdPostDTO.getTitle());
        assertEquals(postDTO.getBody(), createdPostDTO.getBody());
    }

    @Test
    void testGetAllPosts() {
        Post post1 = new Post();
        post1.setTitle("Post 1");
        post1.setBody("Content 1");

        Post post2 = new Post();
        post2.setTitle("Post 2");
        post2.setBody("Content 2");

        PostDTO postDTO1 = new PostDTO();
        postDTO1.setTitle("Post 1");
        postDTO1.setBody("Content 1");

        PostDTO postDTO2 = new PostDTO();
        postDTO2.setTitle("Post 2");
        postDTO2.setBody("Content 2");

        List<Post> posts = Arrays.asList(post1, post2);
        List<PostDTO> postDTOs = Arrays.asList(postDTO1, postDTO2);

        when(postRepository.findAll()).thenReturn(posts);
        when(postMapper.postToPostDTO(post1)).thenReturn(postDTO1);
        when(postMapper.postToPostDTO(post2)).thenReturn(postDTO2);

        List<PostDTO> result = postService.getAllPosts();

        assertNotNull(result);
        assertEquals(postDTOs.size(), result.size());
        assertEquals(postDTOs.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(postDTOs.get(1).getTitle(), result.get(1).getTitle());
    }

    @Test
    void testGetPostById() {
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        post.setTitle("Test Post");
        post.setBody("Test Content");

        PostDTO postDTO = new PostDTO();
        postDTO.setId(postId);
        postDTO.setTitle("Test Post");
        postDTO.setBody("Test Content");

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postMapper.postToPostDTO(post)).thenReturn(postDTO);

        PostDTO result = postService.getPostById(postId);

        assertNotNull(result);
        assertEquals(postDTO.getId(), result.getId());
        assertEquals(postDTO.getTitle(), result.getTitle());
        assertEquals(postDTO.getBody(), result.getBody());
    }

    @Test
    void testUpdatePost() {
        Long postId = 1L;
        Post existingPost = new Post();
        existingPost.setId(postId);
        existingPost.setTitle("Existing Post");
        existingPost.setBody("Existing Content");
        existingPost.setCreatedAt(LocalDateTime.now().minusDays(1));

        PostDTO updatedPostDTO = new PostDTO();
        updatedPostDTO.setTitle("Updated Post");
        updatedPostDTO.setBody("Updated Content");

        Post updatedPost = new Post();
        updatedPost.setId(postId);
        updatedPost.setTitle("Updated Post");
        updatedPost.setBody("Updated Content");
        updatedPost.setCreatedAt(existingPost.getCreatedAt());
        updatedPost.setUpdatedAt(LocalDateTime.now());

        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));
        when(postMapper.postDTOToPost(updatedPostDTO)).thenReturn(updatedPost);
        when(postRepository.save(updatedPost)).thenReturn(updatedPost);
        when(postMapper.postToPostDTO(updatedPost)).thenReturn(updatedPostDTO);

        PostDTO result = postService.update(postId, updatedPostDTO);

        assertNotNull(result);
        assertEquals(updatedPostDTO.getTitle(), result.getTitle());
        assertEquals(updatedPostDTO.getBody(), result.getBody());
    }
}