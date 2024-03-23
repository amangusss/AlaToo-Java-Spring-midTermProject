package com.example.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private long id;
    private String title;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
