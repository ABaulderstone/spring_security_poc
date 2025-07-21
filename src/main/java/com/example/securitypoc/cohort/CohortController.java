package com.example.securitypoc.cohort;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.securitypoc.cohort.entities.Cohort;

import java.util.List;
import java.util.Optional;

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
        Cohort foundCohort = result.orElseThrow();
        return ResponseEntity.ok(foundCohort);
    }

}
