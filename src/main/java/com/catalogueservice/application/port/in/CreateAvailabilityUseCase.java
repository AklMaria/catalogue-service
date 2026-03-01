package com.catalogueservice.application.port.in;

import com.catalogueservice.domain.model.ResourceAvailability;

public interface CreateAvailabilityUseCase {
    ResourceAvailability create(CreateAvailabilityCommand command);
}