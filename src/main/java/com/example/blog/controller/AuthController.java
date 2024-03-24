package com.example.blog.controller;

import com.example.blog.dto.UserRequest;
import com.example.blog.dto.UserResponse;
import com.example.blog.entity.Users;
import com.example.blog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@Tag(
        name = "Authorization Controller",
        description = "Controller for operations with users"
)
public class AuthController {

    private final UserService service;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(
            summary = "User registrations"
    )
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(service.registerUser(userRequest));
    }

    @Operation(
            summary = "User login"
    )
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Users users) {
        Users foundUsers = service.findByEmail(users.getEmail());
        if (foundUsers != null && passwordEncoder.matches(users.getPassword(), foundUsers.getPassword())) {
            return ResponseEntity.ok("Successful Authorization");
        } else {
            return ResponseEntity.badRequest().body("Incorrect credentials");
        }
    }
}
