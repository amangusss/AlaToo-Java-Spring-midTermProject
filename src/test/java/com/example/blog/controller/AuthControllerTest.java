package com.example.blog.controller;

import com.example.blog.dto.UserLogin;
import com.example.blog.dto.UserRequest;
import com.example.blog.dto.UserResponse;
import com.example.blog.entity.Users;
import com.example.blog.exception.IncorrectPasswordException;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.mapper.UserMapper;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRequest userRequest;
    private UserResponse userResponse;
    private Users user;
    private UserLogin userLogin;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
        userRequest.setUsername("test@example");
        userRequest.setPassword("password");
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");

        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setUsername("test@example");
        userResponse.setFirstName("John");
        userResponse.setLastName("Doe");

        user = new Users();
        user.setId(1L);
        user.setUsername("test@example");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");

        userLogin = new UserLogin();
        userLogin.setUsername("test@example");
        userLogin.setPassword("password");
    }

    @Test
    void registerUser() {
        when(userMapper.toEntity(userRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userResponse);

        UserResponse result = userService.registerUser(userRequest);

        assertNotNull(result);
        assertEquals(userResponse, result);
        verify(userMapper, times(1)).toEntity(userRequest);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDTO(user);
    }
}