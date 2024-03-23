package com.example.blog.service;

import com.example.blog.dto.UserDTO;
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

    public UserDTO registerUser(UserDTO userDTO) {
        Users user = userMapper.userDTOToUsers(userDTO);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.USER);
        Users savedUser = userRepository.save(user);
        return userMapper.userToUserDTO(savedUser);
    }

    public Users findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
