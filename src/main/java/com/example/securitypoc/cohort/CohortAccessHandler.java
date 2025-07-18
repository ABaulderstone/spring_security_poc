package com.example.securitypoc.cohort;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.securitypoc.auth.CurrentUserService;
import com.example.securitypoc.cohort.entities.Cohort;
import com.example.securitypoc.user.entities.User;

@Component
public class CohortAccessHandler {
    private final CurrentUserService currentUserService;
    private final CohortRepository cohortRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CohortAccessHandler(CurrentUserService currentUserService, CohortRepository cohortRepository,
            EnrollmentRepository enrollmentRepository) {
        this.currentUserService = currentUserService;
        this.cohortRepository = cohortRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Cohort> visibleCohorts() {
        User currentUser = currentUserService.getUserEntity();
        switch (currentUser.getRole()) {
            case ADMIN, TALENT, COACH:
                return cohortRepository.findAll();
            case STUDENT:
                return enrollmentRepository.findActiveByUser(currentUser).map(e -> List.of(e.getCohort()))
                        .orElse(List.of());
            default:
                return List.of();
        }
    }

}
