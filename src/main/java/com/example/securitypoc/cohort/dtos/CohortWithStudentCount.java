package com.example.securitypoc.cohort.dtos;

import java.time.LocalDate;

public record CohortWithStudentCount(Long id, String name, LocalDate startDate, Long studentCount)
        implements CohortResponse {

}
