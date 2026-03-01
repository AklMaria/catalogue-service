package com.catalogueservice.infrastructure.adapter.in.messaging.dto;

import java.time.Instant;
import java.util.UUID;

public record BookingCancelRequestedEvent(
        UUID bookingId,
        UUID availabilityId,
        Instant timestamp
) {}