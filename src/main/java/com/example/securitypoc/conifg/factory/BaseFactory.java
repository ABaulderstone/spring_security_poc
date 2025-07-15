package com.example.securitypoc.conifg.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.javafaker.Faker;

public abstract class BaseFactory {
    private final Faker faker = new Faker();
    private static final AtomicInteger counter = new AtomicInteger();

    protected LocalDate randomPastDate(int monthsAgo) {

        int days = faker.number().numberBetween(1, monthsAgo * 30);
        return Instant.ofEpochMilli(faker.date().past(days, TimeUnit.DAYS).getTime()).atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    protected int incrementAndGet() {
        return counter.incrementAndGet();
    }

    protected Faker faker() {
        return faker;
    }

    public abstract boolean repoEmpty();

}
