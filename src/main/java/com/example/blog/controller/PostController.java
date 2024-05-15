package com.example.blog.controller;

import com.example.blog.dto.PostRequest;
import com.example.blog.dto.PostResponse;
import com.example.blog.service.impl.PostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(
        name = "Post Controller",
        description = "Controller for operations with posts"

)
public class PostController {

    private final PostServiceImpl service;

    private PostController(PostServiceImpl service) {
        this.service = service;
    }

    @Operation(
            summary = "Creating a Post"
    )
    @PostMapping
    public ResponseEntity<PostResponse> createPost(Long userId, @RequestBody PostRequest postRequest) {
        return service.createPost(userId, postRequest);
    }

    @Operation(
            summary = "Returning a page of all Posts"
    )
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = service.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Operation(
            summary = "Returning a page of post by it's Id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse post = service.getPostById(id);
        return post != null
                ? new ResponseEntity<>(post, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Updating a Post"
    )
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(Long userId, @PathVariable Long id, @RequestBody PostRequest postRequest) {
        return postRequest != null
                ? new ResponseEntity<>(service.update(userId, id, postRequest), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Deleting a Post"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(Long userId, @PathVariable Long id) {
        service.deletePost(userId, id);
      return ResponseEntity.ok("Deleted successfully");
    }
}
