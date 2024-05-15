package com.example.blog.dto;

import com.example.blog.entity.UserRole;
import com.example.blog.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class CustomUserDetails extends Users implements UserDetails {

    private Users user;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Users byUsername) {
        this.user = user;
        List<GrantedAuthority> auths = new ArrayList<>();
        for(UserRole role : byUsername.getRoles()){
            auths.add(new SimpleGrantedAuthority(role.getRolename().name().toUpperCase()));
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(user.getRoles().split(", "))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}