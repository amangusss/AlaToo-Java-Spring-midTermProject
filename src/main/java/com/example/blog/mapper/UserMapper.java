package com.example.blog.mapper;

import com.example.blog.dto.UserRequest;
import com.example.blog.dto.UserResponse;
import com.example.blog.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INStANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    UserResponse toModel(Users users);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "posts", ignore = true)
    Users toEntity(UserRequest userRequest);
}
