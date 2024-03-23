package com.example.blog.mapper;

import com.example.blog.dto.UserResponse;
import com.example.blog.entity.Users;
import com.example.blog.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INStANCE = Mappers.getMapper(UserMapper.class);

    UserResponse usersToUserResponse(UserDTO users);

    UserDTO userToUserDTO(Users users);
    Users userDTOToUsers(UserDTO userDTO);
}
