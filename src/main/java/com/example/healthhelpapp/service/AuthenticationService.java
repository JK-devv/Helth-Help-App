package com.example.healthhelpapp.service;

import com.example.healthhelpapp.dto.UserEntity;

public interface AuthenticationService {

    String authenticate(UserEntity authentication);
}
