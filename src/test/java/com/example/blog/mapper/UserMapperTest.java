package com.example.blog.mapper;

import com.example.blog.dto.UserRequest;
import com.example.blog.dto.UserResponse;
import com.example.blog.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    @Autowired
    private final UserMapper userMapper = UserMapper.INStANCE;

    @Test
    void testToModel() {
        Users user = new Users();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");

        UserResponse userResponse = userMapper.toModel(user);

        assertEquals(user.getId(), userResponse.getId());
        assertEquals(user.getEmail(), userResponse.getEmail());
    }

    @Test
    void testToEntity() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("password");

        Users user = userMapper.toEntity(userRequest);

        assertEquals(userRequest.getEmail(), user.getEmail());
        assertEquals(userRequest.getPassword(), user.getPassword());
    }
}