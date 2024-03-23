package com.example.blog.dto;

import com.example.blog.enums.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private String password;
}
