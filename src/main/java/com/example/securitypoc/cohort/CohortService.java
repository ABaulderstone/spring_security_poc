package com.example.securitypoc.cohort;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.securitypoc.cohort.entities.Cohort;
import com.example.securitypoc.common.Either;
import com.example.securitypoc.common.exception.NotFoundError;
import com.example.securitypoc.common.exception.ServiceError;

@Service
public class CohortService {

    private final CohortAccessHandler accessHandler;

    public CohortService(CohortAccessHandler accessHandler) {
        this.accessHandler = accessHandler;
    }

    public List<Cohort> findAll() {
        return accessHandler.visibleCohorts();
    }

    public Either<ServiceError, Cohort> findById(Long id) {
        Optional<Cohort> result = this.accessHandler.visibleCohort(id);
        if (result.isEmpty()) {
            NotFoundError err = new NotFoundError("Cohort", id);
            return Either.left(err);
        }
        return Either.right(result.get());
    }

}
