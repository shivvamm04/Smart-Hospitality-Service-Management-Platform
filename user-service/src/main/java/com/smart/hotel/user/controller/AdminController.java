package com.smart.hotel.user.controller;

import com.smart.hotel.dto.ApiResponse;
import com.smart.hotel.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final AdminService adminService;

    @PostMapping("/{userId}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<?> activateUser(@PathVariable Long userId) {
        adminService.activateUser(userId);
        return ApiResponse.success("User activated successfully",null);
    }

    @PostMapping("/{userId}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<?> deactivateUser(@PathVariable Long userId) {
        adminService.deactivateUser(userId);
        return ApiResponse.success("User deactivated successfully",null);
    }
}

