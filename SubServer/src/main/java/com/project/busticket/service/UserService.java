package com.project.busticket.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.busticket.dto.request.user.UserCreationRequest;
import com.project.busticket.dto.request.user.UserUpdateRequest;
import com.project.busticket.dto.response.UserResponse;
import com.project.busticket.entity.Users;
import com.project.busticket.enums.Role;
import com.project.busticket.exception.Appexception;
import com.project.busticket.mapper.UsersMapper;
import com.project.busticket.exception.ErrorCode;
import com.project.busticket.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UsersMapper userMapper;

    PasswordEncoder passwordEncoder;

    public Users creatUser(UserCreationRequest request) {
        if (userRepository.existsByUserName(request.getUserName()))
            throw new Appexception(ErrorCode.USER_EXISTED);

        Users user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> role = new HashSet<>();
        role.add(Role.USER.name());

        user.setRole(role);

        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUser() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        List<Users> userList = userRepository.findByUserName(name);
        if (userList.isEmpty())
            throw new Appexception(ErrorCode.USER_NOT_EXISTED);

        Users user = userList.get(0);

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateInfo(UserUpdateRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        List<Users> userList = userRepository.findByUserName(name);
        if (userList.isEmpty())
            throw new Appexception(ErrorCode.USER_NOT_EXISTED);

        Users user = userList.get(0);

        userMapper.updateInfo(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            return "User does not exist";
        }

        else {
            userRepository.deleteById(id);
            return "User has been deleted!";
        }
    }
}
