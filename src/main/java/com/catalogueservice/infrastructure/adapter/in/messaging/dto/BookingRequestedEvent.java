package com.catalogueservice.infrastructure.adapter.in.messaging.dto;

import java.time.Instant;
import java.util.UUID;

public record BookingRequestedEvent(
        UUID bookingId,
        UUID availabilityId,
        String note,
        Instant timestamp
) {}