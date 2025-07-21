package com.example.securitypoc.cohort;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.securitypoc.cohort.entities.Cohort;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

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

}
