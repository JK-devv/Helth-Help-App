package com.example.healthhelpapp.service.impl;

import com.example.healthhelpapp.dto.UserEntity;
import com.example.healthhelpapp.security.JwtService;
import com.example.healthhelpapp.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;

    public String authenticate(UserEntity authentication) {
        return jwtService.generateToken(userService
                .loadUserByUsername(authentication.getUsername()));
    }
}
