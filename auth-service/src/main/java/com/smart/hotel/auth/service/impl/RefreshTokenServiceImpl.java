package com.smart.hotel.auth.service.impl;

import com.smart.hotel.auth.entity.RefreshToken;
import com.smart.hotel.auth.entity.User;
import com.smart.hotel.auth.repository.RefreshTokenRepository;
import com.smart.hotel.auth.service.RefreshTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository repository;

    @Override
    public RefreshToken create(User user) {
        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(Instant.now().plus(30, ChronoUnit.DAYS));
        return repository.save(token);
    }

    @Override
    public RefreshToken validate(String token) {
        System.out.println("--- validating token" + token );
        RefreshToken rt = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (rt.isRevoked() || rt.isUsed()) {
            throw new RuntimeException("Refresh token revoked");
        }

        if (rt.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        rt.setUsed(true);
        repository.save(rt);

        return rt;
    }

    public void logout (String refreshToken){
        RefreshToken token = repository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        token.setRevoked(true);
        repository.save(token);
    }

    @Override
    @Transactional
    public void logoutAll(Long userId) {
        System.out.println("--- userId in implimentation "+userId);
        repository.revokeAllByUserId(userId);
    }

}

