package com.example.securitypoc.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.securitypoc.user.entities.User;

@Service
public class CurrentUserService {
    public User getUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated())
            return null;

        Object principal = auth.getPrincipal();
        return (principal instanceof UserDetailsImpl userDetails) ? userDetails.getUser() : null;
    }
}
