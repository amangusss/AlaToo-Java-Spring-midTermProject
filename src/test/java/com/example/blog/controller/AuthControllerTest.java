package com.example.blog.controller;

import com.example.blog.dto.UserRequest;
import com.example.blog.dto.UserResponse;
import com.example.blog.entity.Users;
import com.example.blog.mapper.UserMapper;
import com.example.blog.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthController authController;

    @Test
    void testRegisterUser() {
        UserRequest userRequest = new UserRequest();
        Users user = new Users();
        UserResponse userResponse = new UserResponse();

        when(userMapper.toEntity(userRequest)).thenReturn(user);
        when(userService.registerUser(any(Users.class))).thenReturn(user);

        ResponseEntity<UserResponse> responseEntity = authController.registerUser(userRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userResponse, responseEntity.getBody());
    }

}