package com.example.securitypoc.auth;

import org.springframework.web.bind.annotation.RestController;

import com.example.securitypoc.auth.dtos.LoginDto;
import com.example.securitypoc.user.dtos.SimpleUserResponseDTO;
import com.example.securitypoc.user.entities.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController()
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final CurrentUserService currentUserService;
    private final ModelMapper mapper;

    public AuthController(AuthService authService, CurrentUserService currentUserService, ModelMapper mapper) {
        this.authService = authService;
        this.currentUserService = currentUserService;
        this.mapper = mapper;
    }

    @PostMapping("/login")
    public ResponseEntity<SimpleUserResponseDTO> login(@Valid @RequestBody LoginDto data,
            HttpServletResponse response) {
        String jwt = this.authService.login(data);
        Cookie jwtCookie = new Cookie("jwt", jwt);
        jwtCookie.setHttpOnly(true);
        // TODO: Only set to false in dev
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(jwtCookie);
        User loggedInUser = this.currentUserService.getUserEntity();
        SimpleUserResponseDTO userResponse = mapper.map(loggedInUser, SimpleUserResponseDTO.class);
        return ResponseEntity.ok(userResponse);

    }

}
