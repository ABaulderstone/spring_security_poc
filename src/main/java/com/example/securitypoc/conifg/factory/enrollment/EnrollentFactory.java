package com.example.securitypoc.conifg.factory.enrollment;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.securitypoc.cohort.EnrollmentRepository;
import com.example.securitypoc.cohort.entities.Cohort;
import com.example.securitypoc.cohort.entities.Enrollment;
import com.example.securitypoc.conifg.factory.BaseFactory;
import com.example.securitypoc.user.entities.User;

@Component
@Profile({ "dev", "test" })
public class EnrollentFactory extends BaseFactory {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollentFactory(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public boolean repoEmpty() {
        return this.enrollmentRepository.count() == 0;
    }

    public Enrollment createAndPersist(User user, Cohort cohort) {
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCohort(cohort);

        LocalDateTime baseDate = user.getCreatedAt().isAfter(cohort.getCreatedAt())
                ? user.getCreatedAt()
                : cohort.getCreatedAt();
        LocalDateTime enrollmentTime = randomDateAfter(baseDate, 1, 7);
        enrollment.setDateEnrolled(enrollmentTime.toLocalDate());
        enrollment.setActive(true);
        this.enrollmentRepository.save(enrollment);
        return enrollment;

    }

}
