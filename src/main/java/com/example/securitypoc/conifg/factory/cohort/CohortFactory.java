package com.example.securitypoc.conifg.factory.cohort;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.securitypoc.cohort.CohortRepository;
import com.example.securitypoc.cohort.entities.Cohort;
import com.example.securitypoc.conifg.factory.BaseFactory;

public class CohortFactory extends BaseFactory {
    private final CohortRepository cohortRepo;

    public CohortFactory(CohortRepository cohortRepo) {
        this.cohortRepo = cohortRepo;
    }

    @Override
    public boolean repoEmpty() {
        return this.cohortRepo.count() == 0;
    }

    public Cohort create(CohortFactoryOptions options) {
        Cohort cohort = new Cohort();
        cohort.setName(options.name != null ? options.name : faker().country().capital());
        cohort.setStartDate(options.startDate != null ? options.startDate : randomPastDate(4));
        return cohort;
    }

    public Cohort create() {
        return create(new CohortFactoryOptions());
    }

    public Cohort createAndPersist(CohortFactoryOptions options) {
        Cohort cohort = create(options);
        this.cohortRepo.save(cohort);
        return cohort;
    }

    public Cohort createAndPersist() {
        return createAndPersist(new CohortFactoryOptions());
    }

    public void persistAll(List<Cohort> cohorts) {
        this.cohortRepo.saveAllAndFlush(cohorts);
    }

}
