package com.example.securitypoc.user.entities;

import java.time.LocalTime;

import com.example.securitypoc.auth.role.Role;
import com.example.securitypoc.common.entity.BaseEntity;
import com.example.securitypoc.common.entity.traits.Timestampable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements Timestampable {
    private LocalTime createdAt;
    private LocalTime updatedAt;
    private String email;
    private String password;
    private Role role;

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

    public LocalTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
