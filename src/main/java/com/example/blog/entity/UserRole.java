package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROLES")
public class UserRole {

    public static final UserRole admin = new UserRole(Name.ADMIN);
    public static final UserRole user = new UserRole(Name.USER);

    @Id
    private UserRole.Name rolename;

    @PrePersist
    void defaultRole() {
        if (rolename == null)
            rolename = Name.USER;
    }

    public enum Name {
        ADMIN,
        USER,
    }

}
