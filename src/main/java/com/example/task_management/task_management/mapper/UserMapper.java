package com.example.task_management.task_management.mapper;

import com.example.task_management.task_management.dto.UserDto;
import com.example.task_management.task_management.entity.UserEntity;

public class UserMapper {
    public static UserEntity toUser(UserDto userDto) {
        return new UserEntity(userDto.getId(), userDto.getUserName(), userDto.getPassword());
    }

    public static UserDto toUserDto(UserEntity user) {
        return new UserDto(user.getId(), user.getUserName(), user.getPassword());
    }
}
