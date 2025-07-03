package com.example.securitypoc.auth;

import org.springframework.web.bind.annotation.RestController;

import com.example.securitypoc.auth.dtos.LoginDto;
import com.example.securitypoc.user.entities.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController()
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final CurrentUserService currentUserService;

    public AuthController(AuthService authService, CurrentUserService currentUserService) {
        this.authService = authService;
        this.currentUserService = currentUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody LoginDto data, HttpServletResponse response) {
        String jwt = this.authService.login(data);
        Cookie jwtCookie = new Cookie("jwt", jwt);
        jwtCookie.setHttpOnly(true);
        // TODO: Only set to false in dev
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(jwtCookie);
        User loggedInUser = this.currentUserService.getUserEntity();
        return ResponseEntity.ok(loggedInUser);

    }

}
