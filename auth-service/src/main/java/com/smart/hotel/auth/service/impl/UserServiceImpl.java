package com.smart.hotel.auth.service.impl;

import com.smart.hotel.auth.dto.LoginRequest;
import com.smart.hotel.auth.dto.LoginResponse;
import com.smart.hotel.auth.dto.UserRegisterRequest;
import com.smart.hotel.auth.dto.UserResponse;
import com.smart.hotel.auth.entity.RefreshToken;
import com.smart.hotel.auth.entity.User;
import com.smart.hotel.auth.repository.UserRepository;
import com.smart.hotel.auth.security.CustomUserDetails;
import com.smart.hotel.auth.security.JwtUtil;
import com.smart.hotel.auth.service.RefreshTokenService;
import com.smart.hotel.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

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

//        --not needed if using  Authentication auth

//        User user = userRepository.findByMobile(request.getMobile())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        if (!user.isActive()) {
//            throw new RuntimeException("User is inactive");
//        }

        Authentication auth =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getMobile(),
                                request.getPassword()
                        )
                );

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        System.out.println("--userDetails "+ userDetails.toString());
        System.out.println("--user "+ user.toString());
        System.out.println("--auth "+ auth);

        String token = jwtUtil.generateToken(auth);

        RefreshToken refreshToken = refreshTokenService.create(user);
        System.out.println("--refreshToken "+ refreshToken.toString());
        System.out.println("--refreshToken get token in login service "+ refreshToken.getToken());

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getMobile(),
                user.getRole(),
                token,
                refreshToken.getToken()
        );
    }

}
