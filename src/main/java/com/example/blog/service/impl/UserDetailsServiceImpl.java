package com.example.blog.service.impl;

import com.example.blog.dto.CustomUserDetails;
import com.example.blog.entity.Users;
import com.example.blog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        logger.debug("Entering in loadUserByUsername Method...");
//        Optional <Users> user = userRepository.findByUsername(username);
//        if(user.isEmpty()){
//            logger.error("Username not found: " + username);
//            throw new UsernameNotFoundException("could not found user..!!");
//        }
//        logger.info("User Authenticated Successfully..!!!");
//        return new CustomUserDetails(user);
//    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AtomicReference<UserDetails> userDetails = new AtomicReference<>();
        Optional<Users> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));
        optionalUser.ifPresentOrElse(
                user -> userDetails.set(new CustomUserDetails(user)),
                ()->{
                    throw new UsernameNotFoundException(
                            String.format("User with username: %s not found", username));
                });
        return userDetails.get();
    }

}
