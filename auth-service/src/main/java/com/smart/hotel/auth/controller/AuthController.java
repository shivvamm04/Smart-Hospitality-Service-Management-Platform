package com.smart.hotel.auth.controller;

import com.smart.hotel.auth.dto.LoginRequest;
import com.smart.hotel.auth.dto.LoginResponse;
import com.smart.hotel.auth.dto.UserRegisterRequest;
import com.smart.hotel.auth.dto.UserResponse;
import com.smart.hotel.auth.entity.User;
import com.smart.hotel.auth.service.UserService;
import com.smart.hotel.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        return ApiResponse.success("User registered successfully", userService.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(
                "Login successful",
                userService.login(request)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/test")
    public String adminTest() {
        return "Admin Access Granted";
    }


}
