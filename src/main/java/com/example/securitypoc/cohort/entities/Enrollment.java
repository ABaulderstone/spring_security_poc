package com.example.securitypoc.cohort.entities;

import java.time.LocalDate;

import com.example.securitypoc.common.entity.BaseEntity;
import com.example.securitypoc.user.entities.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "enrollments")
public class Enrollment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "cohort_id")
    private Cohort cohort;

    @Column
    private LocalDate dateEnrolled;
    @Column
    private boolean active;

    public Enrollment() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cohort getCohort() {
        return cohort;
    }

    public void setCohort(Cohort cohort) {
        this.cohort = cohort;
    }

    public LocalDate getDateEnrolled() {
        return dateEnrolled;
    }

    public void setDateEnrolled(LocalDate dateEnrolled) {
        this.dateEnrolled = dateEnrolled;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean isActive) {
        this.active = isActive;
    }

}