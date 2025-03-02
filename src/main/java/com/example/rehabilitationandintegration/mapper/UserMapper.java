package com.example.rehabilitationandintegration.mapper;

import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.model.UserDto;
import com.example.rehabilitationandintegration.model.request.RegisterDto;
import com.example.rehabilitationandintegration.model.request.UserUpdateDto;
import com.example.rehabilitationandintegration.model.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    UserEntity forUpdate(UserUpdateDto userUpdateDto, @MappingTarget UserEntity userEntity);

    UserEntity toEntity(UserDto userDto);

    UserDto toDto(UserEntity userEntity);

    UserEntity toUserEntity(RegisterDto registerDto);

    UserResponseDto toResponse(UserEntity userEntity);

}
