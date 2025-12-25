package com.smart.hotel.auth.service.impl;

import com.smart.hotel.auth.dto.LoginRequest;
import com.smart.hotel.auth.dto.LoginResponse;
import com.smart.hotel.auth.dto.UserRegisterRequest;
import com.smart.hotel.auth.dto.UserResponse;
import com.smart.hotel.auth.entity.User;
import com.smart.hotel.auth.repository.UserRepository;
import com.smart.hotel.auth.security.JwtUtil;
import com.smart.hotel.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    public UserResponse register(UserRegisterRequest request) {

        if (userRepository.existsByMobile(request.getMobile())) {
            throw new RuntimeException("Mobile no. already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .mobile(request.getMobile())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .active(true)
                .build();

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getMobile(),
                savedUser.getRole()
        );
    }
    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByMobile(request.getMobile())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!user.isActive()) {
            throw new RuntimeException("User is inactive");
        }

        String token = jwtUtil.generateToken(
                user.getMobile(),
                user.getRole().name()
        );
        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getMobile(),
                user.getRole(),
                token
        );
    }

}
