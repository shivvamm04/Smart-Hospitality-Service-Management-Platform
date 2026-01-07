package com.smart.hotel.user.service;

public interface AdminService {

    void activateUser(Long userId);

    void deactivateUser(Long userId);
}

