package com.smart.hotel.auth.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        System.out.println("-- inside JwUtil constructor");
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("-- inside do Filter Internal");
        String header = request.getHeader("Authorization");

        System.out.println("-- request "+ request);
        System.out.println("-- header "+ header);

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);
            System.out.println("-- token "+ token);
            try {
                    Claims claims = jwtUtil.extractClaims(token);
                    System.out.println("-- claims "+ claims);

                    String mobile = claims.getSubject();
                    String role = claims.get("role", String.class);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    mobile,
                                    null,
                                    List.of(new SimpleGrantedAuthority(role))
                            );
                System.out.println("-- authentication "+ authentication);
                System.out.println("-- security Holder context before save "+ SecurityContextHolder.getContext());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("-- security Holder context after save "+ SecurityContextHolder.getContext());

                } catch (Exception e) {
                System.out.println("--- failing");
                // Invalid token → do not authenticate
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
