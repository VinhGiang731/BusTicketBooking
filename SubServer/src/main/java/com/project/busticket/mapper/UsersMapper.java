package com.project.busticket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.project.busticket.dto.request.user.UserCreationRequest;
import com.project.busticket.dto.request.user.UserUpdateRequest;
import com.project.busticket.dto.response.UserResponse;
import com.project.busticket.entity.Users;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    Users toUser(UserCreationRequest request);

    UserResponse toUserResponse(Users user);

    void updateInfo(@MappingTarget Users user, UserUpdateRequest request);
}
