package com.example.securitypoc.cohort;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.securitypoc.cohort.entities.Cohort;
import com.example.securitypoc.common.Either;
import com.example.securitypoc.common.exception.HTTPException;
import com.example.securitypoc.common.exception.NotFoundError;
import com.example.securitypoc.common.exception.ServiceError;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController()
@RequestMapping("/cohorts")
public class CohortController {
    private final CohortService cohortService;

    public CohortController(CohortService cohortService) {
        this.cohortService = cohortService;
    }

    @GetMapping()
    public ResponseEntity<List<Cohort>> findAll() {

        var cohorts = this.cohortService.findAll();
        return ResponseEntity.ok(cohorts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cohort> getMethodName(@PathVariable Long id) {
        Either<ServiceError, Cohort> result = this.cohortService.findById(id);
        if (result.isLeft()) {
            var err = result.getLeft();
            if (err instanceof NotFoundError) {
                throw new HTTPException(HttpStatus.NOT_FOUND, err);
            }
            throw new HTTPException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Cohort foundCohort = result.getRight();
        return ResponseEntity.ok(foundCohort);
    }

}
