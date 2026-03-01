package com.catalogueservice.infrastructure.adapter.in.messaging.dto;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

public record BookingResultEvent(
        UUID bookingId,
        UUID availabilityId,
        String result,   // CONFIRMED | REJECTED | FAILED
        String reason,   // CAPACITY_REACHED | AVAILABILITY_NOT_FOUND | INTERNAL_ERROR
        ResourceSnapshot resource,
        SlotSnapshot slot,
        Instant timestamp
) {
    public record ResourceSnapshot(UUID id, String name, String description) {}
    public record SlotSnapshot(UUID id, ZonedDateTime startDate, ZonedDateTime endDate) {}
}