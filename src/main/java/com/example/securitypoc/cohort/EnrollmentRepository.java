package com.example.securitypoc.cohort;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securitypoc.cohort.entities.Enrollment;
import com.example.securitypoc.user.entities.User;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findActiveByUser(User user);
}
