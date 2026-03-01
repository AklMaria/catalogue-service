package com.catalogueservice.application.port.in;

import com.catalogueservice.domain.model.ResourceAvailability;

import java.util.List;
import java.util.UUID;

public interface ListAvailabilityByResourceUseCase {
    List<ResourceAvailability> listByResource(UUID resourceId);
}