package com.example.securitypoc.conifg.factory.user;

import com.example.securitypoc.auth.role.Role;

public class UserFactoryOptions {
    public String email;
    public String rawPassword;
    public Role role;

    public UserFactoryOptions() {
    }

    public UserFactoryOptions email(String email) {
        this.email = email;
        return this;
    }

    public UserFactoryOptions rawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
        return this;
    }

    public UserFactoryOptions role(Role role) {
        this.role = role;
        return this;
    }

}
