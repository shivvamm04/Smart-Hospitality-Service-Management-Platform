package com.smart.hotel.user.service.impl;

import com.smart.hotel.event.UserEventPublisher;
import com.smart.hotel.user.entity.User;
import com.smart.hotel.user.repository.UserRepository;
import com.smart.hotel.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    @Autowired
    private final UserEventPublisher userEventPublisher;

    @Override
    public void activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(true);
        userRepository.save(user);

        userEventPublisher.publishUserActivated(userId);
    }

    @Override
    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(false);
        userRepository.save(user);

        userEventPublisher.publishUserDeactivated(userId);
    }
}

