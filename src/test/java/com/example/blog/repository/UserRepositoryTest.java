package com.example.blog.repository;

import com.example.blog.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        Users user = new Users();
        user.setEmail("test@example.com");
        user.setPassword("password");

        Users savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    void testFindByEmail() {
        Users user = new Users();
        user.setEmail("test@example.com");
        user.setPassword("password");

        userRepository.save(user);

        Users foundUser = userRepository.findByEmail(user.getEmail()).orElse(null);

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getPassword(), foundUser.getPassword());
    }
}