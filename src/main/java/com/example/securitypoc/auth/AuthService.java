package com.example.securitypoc.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.securitypoc.auth.dtos.LoginDto;
import com.example.securitypoc.auth.jwt.JwtUtils;

@Service
public class AuthService {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserDetailsServiceImpl userDetailsService, JwtUtils jwtUtils,
            AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public String login(LoginDto data) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(data.getEmail());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(data.getEmail(),
                data.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.jwtUtils.generateJwt(authentication);
        System.out.println(userDetails.getUsername());
        return jwt;

    }

}
