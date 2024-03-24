package com.example.blog.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserRequest {
    @Email(message = "Invalid Data")
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
