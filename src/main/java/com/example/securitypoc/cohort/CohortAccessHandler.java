package com.example.securitypoc.cohort;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.securitypoc.auth.CurrentUserService;
import com.example.securitypoc.cohort.dtos.CohortResponse;
import com.example.securitypoc.cohort.entities.Cohort;
import com.example.securitypoc.user.entities.User;

@Component
public class CohortAccessHandler {
    private final CurrentUserService currentUserService;
    private final CohortRepository cohortRepository;

    public CohortAccessHandler(CurrentUserService currentUserService, CohortRepository cohortRepository) {
        this.currentUserService = currentUserService;
        this.cohortRepository = cohortRepository;
    }

    public List<CohortResponse> visibleCohorts() {
        User currentUser = currentUserService.getUserEntity();
        switch (currentUser.getRole()) {
            case ADMIN, TALENT, COACH:
                return cohortRepository.findAllWithStudentCount().stream().map(c -> (CohortResponse) c).toList();
            case STUDENT:
                return cohortRepository.findActiveCohortByUser(currentUser.getId())
                        .map(c -> List.of((CohortResponse) c))
                        .orElse(List.of());
            default:
                return List.of();
        }
    }

    public Optional<Cohort> visibleCohort(Long cohortId) {
        User currentUser = currentUserService.getUserEntity();
        switch (currentUser.getRole()) {
            case ADMIN, TALENT, COACH:
                return cohortRepository.findById(cohortId);
            case STUDENT:
                return cohortRepository.findByIdAndUserEnrolled(cohortId, currentUser.getId());
            default:
                return Optional.empty();
        }
    }

}
