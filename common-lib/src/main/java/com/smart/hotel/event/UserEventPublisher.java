package com.smart.hotel.event;

public interface UserEventPublisher {

    void publishUserRegistered(Long userId);

    void publishUserActivated(Long userId);

    void publishUserDeactivated(Long userId);
}

