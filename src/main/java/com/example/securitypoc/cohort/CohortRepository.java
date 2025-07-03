package com.example.securitypoc.cohort;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securitypoc.cohort.entities.Cohort;

public interface CohortRepository extends JpaRepository<Cohort, Long> {

}
