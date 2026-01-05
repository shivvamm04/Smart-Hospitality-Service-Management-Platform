package com.smart.hotel.auth.controller;

import com.smart.hotel.auth.dto.*;
import com.smart.hotel.auth.entity.RefreshToken;
import com.smart.hotel.auth.entity.User;
import com.smart.hotel.auth.repository.UserRepository;
import com.smart.hotel.auth.security.JwtUtil;
import com.smart.hotel.auth.service.RefreshTokenService;
import com.smart.hotel.auth.service.UserService;
import com.smart.hotel.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @Autowired
    private final UserRepository userRepository;


    public AuthController(UserService userService, RefreshTokenService refreshTokenService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
        userRepository = null;
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

    @GetMapping("/admin/report")
    @PreAuthorize("hasRole('ADMIN')")
    public String getReport() {
        return "report";
    }

    @PostMapping("/refresh")
    public ApiResponse<RefreshResponse> refresh(
            @RequestBody RefreshRequest request) {

        RefreshToken token =
                refreshTokenService.validate(request.getRefreshToken());

        User user = token.getUser();

        String accessToken = jwtUtil.generateToken(user.getMobile(),user.getRole());

        RefreshToken newRefreshToken =
                refreshTokenService.create(user);

        return ApiResponse.success("Refresh token generated successfully",
                new RefreshResponse(
                        user.getId(),
                        user.getName(),
                        user.getMobile(),
                        user.getRole(),
                        accessToken,
                        newRefreshToken.getToken()
                )
        );
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(
            @RequestBody LogoutRequest request) {

        refreshTokenService.logout(request.getRefreshToken());

        return ApiResponse.success("Logged out successfully", null);
    }

    @PostMapping("/logout-all")
    public ApiResponse<String> logoutAll(Authentication authentication) {

        System.out.println("--- authentication.getPrincipal()"+ authentication.getPrincipal());
        String mobile = (String) authentication.getPrincipal();

        User user = userRepository.findByMobile(mobile).orElseThrow();

        System.out.println("--- userId"+ user.getId());
        refreshTokenService.logoutAll(user.getId());
        return ApiResponse.success("Logged out from all devices",null);
    }




}
