package com.example.blog.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import com.example.blog.dto.UserDTO;
import com.example.blog.entity.Users;
import com.example.blog.mapper.UserMapper;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        Users users = new Users();
        users.setEmail("test@example.com");
        users.setPassword("encodedPassword");

        when(userMapper.userDTOToUsers(userDTO)).thenReturn(users);
        when(passwordEncoder.encode(users.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(Users.class))).thenReturn(users);
        when(userMapper.userToUserDTO(users)).thenReturn(userDTO);

        UserDTO registeredUserDTO = userService.registerUser(userDTO);

        assertNotNull(registeredUserDTO);
        assertEquals(userDTO.getEmail(), registeredUserDTO.getEmail());
        assertEquals(userDTO.getRole(), registeredUserDTO.getRole());
    }

    @Test
    void testFindByEmail() {
        String email = "test@example.com";
        Users user = new Users();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Users foundUser = userService.findByEmail(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
    }



}
