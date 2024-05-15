package com.example.blog.service;

import com.example.blog.dto.UserLogin;
import com.example.blog.dto.UserRequest;
import com.example.blog.dto.UserResponse;

public interface UserService {

    UserResponse registerUser(UserRequest userRequest);
}
