package com.example.securitypoc.common.entity.traits;

import java.time.LocalTime;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public interface Timestampable {
    public LocalTime getCreatedAt();

    public void setCreatedAt(LocalTime timestamp);

    public LocalTime getUpdatedAt();

    public void setUpdatedAt(LocalTime timestamp);

    @PrePersist
    default void onCreate() {
        LocalTime timestamp = LocalTime.now();
        setCreatedAt(timestamp);
        setUpdatedAt(timestamp);
    }

    @PreUpdate
    default void onUpdate() {
        setUpdatedAt(LocalTime.now());
    }

}
