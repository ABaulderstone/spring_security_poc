package com.example.securitypoc.conifg.factory.cohort;

import java.time.LocalDate;

public class CohortFactoryOptions {
    public String name;
    public LocalDate startDate;

    public CohortFactoryOptions() {
    }

    public CohortFactoryOptions name(String name) {
        this.name = name;
        return this;
    }

    public CohortFactoryOptions startDate(LocalDate sDate) {
        this.startDate = sDate;
        return this;
    }
}
