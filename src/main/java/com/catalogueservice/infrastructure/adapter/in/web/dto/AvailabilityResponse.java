package com.catalogueservice.infrastructure.adapter.in.web.dto;

import java.util.UUID;

public record AvailabilityResponse(
        UUID id,
        UUID resourceId,
        UUID slotId,
        int capacity,
        int consumed
) {}