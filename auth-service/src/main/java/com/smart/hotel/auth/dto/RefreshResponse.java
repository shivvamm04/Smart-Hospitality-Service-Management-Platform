package com.smart.hotel.auth.dto;

import com.smart.hotel.auth.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshResponse {
    private Long userId;
    private String name;
    private String mobile;
    private Role role;
    private String token;
    private String newRefreshToken;
}
