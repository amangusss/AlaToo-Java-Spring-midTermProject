package com.example.blog.repository;

import com.example.blog.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, UserRole.Name>,
        JpaRepository<UserRole, UserRole.Name> {
}
