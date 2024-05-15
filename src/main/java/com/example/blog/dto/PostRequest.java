package com.example.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequest {
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    private String body;
}
