package com.smart.hotel.user.controller;

import com.smart.hotel.dto.ApiResponse;
import com.smart.hotel.user.entity.User;
import com.smart.hotel.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ApiResponse<?> myProfile(Authentication authentication, HttpServletRequest request) {

        // From JWT
//        String mobile = authentication.getName();
//        String role = authentication.getAuthorities()
//                .iterator()
//                .next()
//                .getAuthority();
//
//        return ApiResponse.success(
//                "Profile fetched successfully",
//                java.util.Map.of(
//                        "mobile", mobile,
//                        "role", role
//                )
//        );

        Long userId = (Long) request.getAttribute("userId");
        String mobile = (String)request.getAttribute("mobile");

        User user = userService.getUserByMobile(mobile);

        return ApiResponse.success(
                "Profile fetched successfully",
                user
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ApiResponse<?> getAllUsers() {

        // Dummy response for now
        List<String> users = List.of("User-1", "User-2", "User-3");

        return ApiResponse.success(
                "All users fetched",
                users
        );
    }

}
