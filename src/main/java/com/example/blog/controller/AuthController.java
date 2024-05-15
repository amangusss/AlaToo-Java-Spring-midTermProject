package com.example.blog.controller;

import com.example.blog.dto.*;
import com.example.blog.entity.RefreshToken;
import com.example.blog.service.JwtService;
import com.example.blog.service.RefreshTokenService;
import com.example.blog.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Authorization Controller",
        description = "Controller for operations with users"
)
public class AuthController {

    private final UserServiceImpl service;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private RefreshTokenService refreshTokenService;

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
    public JwtResponse AuthenticateAndGetToken(@RequestBody UserLogin userLogin){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userLogin.getUsername());
            return JwtResponse.builder()
                    .accessToken(jwtService.GenerateToken(userLogin.getUsername()))
                    .token(refreshToken.getToken())
                    .build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }


}
