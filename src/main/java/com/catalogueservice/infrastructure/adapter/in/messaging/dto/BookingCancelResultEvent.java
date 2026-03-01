package com.catalogueservice.infrastructure.adapter.in.messaging.dto;

import java.time.Instant;
import java.util.UUID;

public record BookingCancelResultEvent(
        UUID bookingId,
        UUID availabilityId,
        String result,   // CANCELLED | FAILED
        String reason,   // INTERNAL_ERROR | AVAILABILITY_NOT_FOUND | NOTHING_TO_RELEASE
        Instant timestamp
) {}