package com.example.blog.mapper;

import com.example.blog.dto.PostDTO;
import com.example.blog.entity.Post;
import org.mapstruct.factory.Mappers;

public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDTO postToPostDTO(Post post);
    Post postDTOToPost(PostDTO postDTO);
}
