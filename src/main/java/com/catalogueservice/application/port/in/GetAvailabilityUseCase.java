package com.catalogueservice.application.port.in;

import com.catalogueservice.domain.model.ResourceAvailability;

import java.util.Optional;
import java.util.UUID;

public interface GetAvailabilityUseCase {
    Optional<ResourceAvailability> getById(UUID id);
}