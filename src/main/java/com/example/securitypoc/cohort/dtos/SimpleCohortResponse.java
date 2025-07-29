package com.example.securitypoc.cohort.dtos;

import java.time.LocalDate;

public record SimpleCohortResponse(Long id, String name, LocalDate startDate) implements CohortResponse {

}
