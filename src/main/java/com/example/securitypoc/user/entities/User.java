package com.example.securitypoc.user.entities;

import java.time.LocalDateTime;

import com.example.securitypoc.auth.role.Role;
import com.example.securitypoc.cohort.entities.Cohort;
import com.example.securitypoc.common.TimeStampEntityListener;
import com.example.securitypoc.common.entity.BaseEntity;
import com.example.securitypoc.common.entity.traits.Timestampable;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@EntityListeners(TimeStampEntityListener.class)
public class User extends BaseEntity implements Timestampable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
