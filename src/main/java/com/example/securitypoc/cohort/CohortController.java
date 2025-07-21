package com.example.securitypoc.cohort;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.securitypoc.cohort.entities.Cohort;
import com.example.securitypoc.common.exception.HTTPException;
import com.example.securitypoc.common.exception.NotFoundError;

import java.util.List;
import java.util.Optional;

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
        Optional<Cohort> result = this.cohortService.findById(id);
        if (result.isEmpty()) {
            NotFoundError err = new NotFoundError("Cohort", id);
            throw new HTTPException(HttpStatus.NOT_FOUND, "NOT_FOUND", err);
        }
        Cohort foundCohort = result.get();
        return ResponseEntity.ok(foundCohort);
    }

}
