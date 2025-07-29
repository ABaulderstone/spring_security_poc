package com.example.securitypoc.cohort.dtos;

public sealed interface CohortResponse permits CohortWithStudentCount, SimpleCohortResponse {
}