package com.example.securitypoc.cohort.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.securitypoc.common.TimeStampEntityListener;
import com.example.securitypoc.common.entity.BaseEntity;
import com.example.securitypoc.common.entity.traits.Timestampable;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;

@Entity
@Table(name = "cohorts")
@EntityListeners(TimeStampEntityListener.class)
public class Cohort extends BaseEntity implements Timestampable {
    private String name;
    private LocalDate startDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Cohort() {
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

}
