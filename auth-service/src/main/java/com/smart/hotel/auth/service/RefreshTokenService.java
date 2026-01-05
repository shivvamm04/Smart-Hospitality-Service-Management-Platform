package com.smart.hotel.auth.service;

import com.smart.hotel.auth.entity.RefreshToken;
import com.smart.hotel.auth.entity.User;

public interface RefreshTokenService {
    RefreshToken create(User user);
    RefreshToken validate(String token);

    void logout(String refreshToken);

    void logoutAll(Long userId);


}

