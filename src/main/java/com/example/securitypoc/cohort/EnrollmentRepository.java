package com.example.securitypoc.cohort;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.securitypoc.cohort.entities.Cohort;
import com.example.securitypoc.cohort.entities.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // JPQL magic to use entity names and dot notation here
    @Query("SELECT e.cohort FROM Enrollment e WHERE e.user.id = :userId AND e.isActive = true")
    Optional<Cohort> findActiveCohortByUser(@Param("userId") Long userId);
}
