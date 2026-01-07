package com.smart.hotel.event.impl;

import com.smart.hotel.event.UserEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DummyUserEventPublisher implements UserEventPublisher {

    @Override
    public void publishUserRegistered(Long userId) {
        System.out.println("[MOCK EVENT] User Registered -> userId = "+ userId);
    }

    @Override
    public void publishUserActivated(Long userId) {
        System.out.println("[MOCK EVENT] User Activated -> userId = "+ userId);
    }

    @Override
    public void publishUserDeactivated(Long userId) {
        System.out.println("[MOCK EVENT] User Deactivated -> userId = "+userId);
    }
}
