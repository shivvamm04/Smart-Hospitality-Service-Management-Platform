package com.smart.hotel.auth.service;

import com.smart.hotel.auth.dto.LoginRequest;
import com.smart.hotel.auth.dto.LoginResponse;
import com.smart.hotel.auth.dto.UserRegisterRequest;
import com.smart.hotel.auth.dto.UserResponse;

public interface UserService {
    UserResponse register(UserRegisterRequest request);
    LoginResponse login(LoginRequest request);
}
