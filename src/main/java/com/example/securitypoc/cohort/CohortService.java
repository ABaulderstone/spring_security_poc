package com.example.securitypoc.cohort;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.securitypoc.cohort.entities.Cohort;

@Service
public class CohortService {

    private final CohortAccessHandler accessHandler;

    public CohortService(CohortAccessHandler accessHandler) {
        this.accessHandler = accessHandler;
    }

    public List<Cohort> findAll() {
        return accessHandler.visibleCohorts();
    }

}
