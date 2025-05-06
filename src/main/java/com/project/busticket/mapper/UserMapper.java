package com.project.busticket.mapper;

import org.mapstruct.Mapper;

import com.project.busticket.dto.request.UserCreationRequest;
import com.project.busticket.dto.response.UserResponse;
import com.project.busticket.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);
}
