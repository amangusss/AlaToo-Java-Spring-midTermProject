package com.example.blog.service;

import com.example.blog.dto.UserRequest;
import com.example.blog.dto.UserResponse;
import com.example.blog.entity.Users;
import com.example.blog.mapper.UserMapper;
import com.example.blog.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    private UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserResponse registerUser(UserRequest userRequest) {
        Users user = userMapper.toEntity(userRequest);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.USER);
        user = userRepository.save(user);
        UserResponse userResponse = userMapper.toModel(user);
        return userResponse;
    }

    public Users findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
