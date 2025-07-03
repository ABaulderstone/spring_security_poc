package com.example.securitypoc.user.dtos;

import com.example.securitypoc.auth.role.Role;

public class SimpleUserResponseDTO {
    private String email;
    private Role role;
    private Long id;

    public SimpleUserResponseDTO() {
    }

    public SimpleUserResponseDTO(String email, Role role, Long id) {
        this.email = email;
        this.role = role;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
