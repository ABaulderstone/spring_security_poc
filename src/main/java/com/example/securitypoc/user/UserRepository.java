package com.example.securitypoc.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securitypoc.user.entities.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
}
