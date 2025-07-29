package com.example.securitypoc.cohort;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.securitypoc.cohort.dtos.CohortWithStudentCount;
import com.example.securitypoc.cohort.dtos.SimpleCohortResponse;
import com.example.securitypoc.cohort.entities.Cohort;

public interface CohortRepository extends JpaRepository<Cohort, Long> {
    // JPQL Magic here allows us to use entity names and dot notation
    // Even though the query is from the enrollment table because we're getting a
    // cohort it belongs on the cohort repo
    @Query("""
                SELECT new com.example.securitypoc.cohort.dtos.SimpleCohortResponse(
                    c.id, c.name, c.startDate
                )
                FROM Enrollment e
                JOIN e.cohort c
                WHERE e.user.id = :userId AND e.active = true
            """)
    Optional<SimpleCohortResponse> findActiveCohortByUser(@Param("userId") Long userId);

    @Query("SELECT c FROM Cohort c JOIN Enrollment e ON c = e.cohort WHERE c.id = :cohortId AND e.user.id = :userId AND e.active = true")
    Optional<Cohort> findByIdAndUserEnrolled(@Param("cohortId") Long cohortId, @Param("userId") Long userId);

    @Query("""
                SELECT new com.example.securitypoc.cohort.dtos.CohortWithStudentCount(
                    c.id,
                    c.name,
                    c.startDate,
                    COUNT(e)
                )
                FROM Cohort c
                LEFT JOIN Enrollment e ON e.cohort = c AND e.active = true AND e.user.role = 'STUDENT'
                GROUP BY c.id, c.name
            """)
    List<CohortWithStudentCount> findAllWithStudentCount();

}
