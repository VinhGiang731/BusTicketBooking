package com.project.busticket.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.busticket.dto.request.user.AuthenticationRequest;
import com.project.busticket.dto.request.user.IntrospectRequest;
import com.project.busticket.dto.response.ApiResponse;
import com.project.busticket.dto.response.AuthenticationResponse;
import com.project.busticket.dto.response.IntrospectResponse;
import com.project.busticket.service.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authApiResponse(@RequestBody AuthenticationRequest request,
            HttpServletRequest httpServletRequest) {
        AuthenticationResponse result = authenticationService.authenticate(request);

        HttpSession session = httpServletRequest.getSession(true);

        session.setAttribute("userName", request.getUserName());
        session.setAttribute("token", result.getToken());
        log.info("UserName: {}", session.getAttribute("userName"));
        log.info("Token: {}", session.getAttribute("token"));

        String scope;
        try {
            IntrospectResponse iResponse = authenticationService
                    .introspect(IntrospectRequest.builder().token(result.getToken()).build());
            scope = iResponse.getScope();
            session.setAttribute("scope", scope);
            log.info("Scope: {}", session.getAttribute("scope"));
        } catch (Exception e) {
            log.error("Get scope is Error: {}", e);
        }
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
}
