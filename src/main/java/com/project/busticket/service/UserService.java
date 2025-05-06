package com.project.busticket.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.busticket.dto.request.UserCreationRequest;
import com.project.busticket.dto.response.UserResponse;
import com.project.busticket.entity.User;
import com.project.busticket.enums.Role;
import com.project.busticket.mapper.UserMapper;
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
    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    public User creatUser(UserCreationRequest request) {
        if (userRepository.existsByUserName(request.getUserName()))
            throw new RuntimeException("User exists");

        User user = userMapper.toUser(request);
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
}
