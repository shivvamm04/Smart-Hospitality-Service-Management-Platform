package com.smart.hotel.auth.service;

import com.smart.hotel.auth.entity.User;
import com.smart.hotel.auth.repository.UserRepository;
import com.smart.hotel.auth.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Spring Security ALWAYS calls this method
    @Override
    public UserDetails loadUserByUsername(String mobile)
            throws UsernameNotFoundException {

        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with mobile: " + mobile)
                );

        return new CustomUserDetails(user);
    }
}
