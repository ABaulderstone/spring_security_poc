package com.example.securitypoc.auth.dtos;

import jakarta.validation.constraints.NotBlank;

public class LoginDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public LoginDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
