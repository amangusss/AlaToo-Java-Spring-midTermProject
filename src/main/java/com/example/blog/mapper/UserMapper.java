package com.example.blog.mapper;

import com.example.blog.dto.UserRequest;
import com.example.blog.dto.UserResponse;
import com.example.blog.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INStANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toModel(Users users);
    Users toEntity(UserRequest userRequest);
}
