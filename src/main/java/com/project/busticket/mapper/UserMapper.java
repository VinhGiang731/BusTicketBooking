package com.project.busticket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.project.busticket.dto.request.user.UserCreationRequest;
import com.project.busticket.dto.request.user.UserUpdateRequest;
import com.project.busticket.dto.response.UserResponse;
import com.project.busticket.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    void updateInfo(@MappingTarget User user, UserUpdateRequest request);
}
