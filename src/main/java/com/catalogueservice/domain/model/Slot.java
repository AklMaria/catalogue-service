package com.catalogueservice.domain.model;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Slot {
    private final UUID id;
    private final ZonedDateTime startDate;
    private final ZonedDateTime endDate;

    private Slot(UUID id, ZonedDateTime startDate, ZonedDateTime endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Slot create(ZonedDateTime startDate, ZonedDateTime endDate) {
        if (startDate == null || endDate == null) throw new IllegalArgumentException("startDate/endDate required");
        if (!endDate.isAfter(startDate)) throw new IllegalArgumentException("endDate must be after startDate");
        return new Slot(UUID.randomUUID(), startDate, endDate);
    }

    public static Slot restore(UUID id, ZonedDateTime startDate, ZonedDateTime endDate) {
        return new Slot(id, startDate, endDate);
    }

    public Slot update(ZonedDateTime startDate, ZonedDateTime endDate) {
        if (startDate == null || endDate == null) throw new IllegalArgumentException("startDate/endDate required");
        if (!endDate.isAfter(startDate)) throw new IllegalArgumentException("endDate must be after startDate");
        return new Slot(this.id, startDate, endDate);
    }

    public UUID getId() { return id; }
    public ZonedDateTime getStartDate() { return startDate; }
    public ZonedDateTime getEndDate() { return endDate; }
}
