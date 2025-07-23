package com.example.securitypoc.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.securitypoc.auth.CurrentUserService;
import com.example.securitypoc.user.dtos.SimpleUserResponseDTO;
import com.example.securitypoc.user.entities.User;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/users")
public class UserController {
    private final CurrentUserService currentUserService;
    private final ModelMapper mapper;

    public UserController(CurrentUserService currentUserService, ModelMapper mapper) {
        this.currentUserService = currentUserService;
        this.mapper = mapper;
    }

    @GetMapping("/current")
    public ResponseEntity<SimpleUserResponseDTO> getCurrentUser() {
        User loggedInUser = this.currentUserService.getUserEntity();
        SimpleUserResponseDTO userResponse = mapper.map(loggedInUser, SimpleUserResponseDTO.class);
        return ResponseEntity.ok(userResponse);
    }

}
