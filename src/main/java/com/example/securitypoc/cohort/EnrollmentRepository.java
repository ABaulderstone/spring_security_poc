package com.example.securitypoc.cohort;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securitypoc.cohort.entities.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

}
