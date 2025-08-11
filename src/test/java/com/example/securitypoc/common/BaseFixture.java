package com.example.securitypoc.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.securitypoc.auth.role.Role;
import com.example.securitypoc.cohort.entities.Cohort;
import com.example.securitypoc.conifg.factory.cohort.CohortFactory;
import com.example.securitypoc.conifg.factory.enrollment.EnrollentFactory;
import com.example.securitypoc.conifg.factory.user.UserFactory;
import com.example.securitypoc.conifg.factory.user.UserFactoryOptions;
import com.example.securitypoc.user.entities.User;

import jakarta.annotation.PostConstruct;

@Component
@Profile("test")
public class BaseFixture {
    private final UserFactory userFactory;
    private final CohortFactory cohortFactory;
    private final EnrollentFactory enrollmentFactory;

    public User adminUser;
    public User coachUser;
    public User talentUser;
    public User studentUser;

    public BaseFixture(UserFactory userFactory,
            CohortFactory cohortFactory,
            EnrollentFactory enrollmentFactory) {
        System.out.println("Base fixture constructor called");
        this.userFactory = userFactory;
        this.cohortFactory = cohortFactory;
        this.enrollmentFactory = enrollmentFactory;
    }

    @PostConstruct
    public void seedBaseData() {
        System.out.println("Seeding from fixture");
        adminUser = userFactory.createAndPersist(
                new UserFactoryOptions()
                        .role(Role.ADMIN)
                        .email("admin@test.com")
                        .rawPassword("admin123"));

        coachUser = userFactory.createAndPersist(
                new UserFactoryOptions()
                        .role(Role.COACH)
                        .email("coach@test.com")
                        .rawPassword("coach123"));

        talentUser = userFactory.createAndPersist(
                new UserFactoryOptions()
                        .role(Role.TALENT)
                        .email("talent@test.com")
                        .rawPassword("talent123"));

        studentUser = userFactory.createAndPersist(
                new UserFactoryOptions()
                        .role(Role.STUDENT)
                        .email("student@test.com")
                        .rawPassword("student123"));

        Cohort cohort = cohortFactory.createAndPersist();
        enrollmentFactory.createAndPersist(coachUser, cohort);
        enrollmentFactory.createAndPersist(studentUser, cohort);
    }
}
