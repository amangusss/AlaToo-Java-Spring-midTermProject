package com.example.blog.controller;

import com.example.blog.dto.UserDTO;
import com.example.blog.dto.UserResponse;
import com.example.blog.entity.Users;
import com.example.blog.mapper.UserMapper;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserDTO userDTO) {
        Users users = userMapper.userDTOToUsers(userDTO);
        UserDTO registeredUsers = userService.registerUser(userMapper.userToUserDTO(users));
        UserResponse userResponse = userMapper.usersToUserResponse(registeredUsers);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Users users) {
        Users foundUsers = userService.findByEmail(users.getEmail());
        if (foundUsers != null && passwordEncoder.matches(users.getPassword(), foundUsers.getPassword())) {
            return ResponseEntity.ok("Successful Authorization");
        } else {
            return ResponseEntity.badRequest().body("Incorrect credentials");
        }
    }
}
