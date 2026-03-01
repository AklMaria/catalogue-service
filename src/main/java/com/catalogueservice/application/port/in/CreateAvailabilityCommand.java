package com.catalogueservice.application.port.in;

import java.util.UUID;

public record CreateAvailabilityCommand(UUID resourceId, UUID slotId, int capacity) {}