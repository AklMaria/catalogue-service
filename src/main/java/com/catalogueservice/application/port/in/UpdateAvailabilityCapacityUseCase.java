package com.catalogueservice.application.port.in;

import com.catalogueservice.domain.model.ResourceAvailability;

import java.util.Optional;
import java.util.UUID;

public interface UpdateAvailabilityCapacityUseCase {
    Optional<ResourceAvailability> updateCapacity(UUID id, UpdateAvailabilityCapacityCommand command);
}