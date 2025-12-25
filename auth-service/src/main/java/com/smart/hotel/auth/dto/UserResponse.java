package com.smart.hotel.auth.dto;

import com.smart.hotel.auth.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String mobile;
    private Role role;
}
