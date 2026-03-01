package com.catalogueservice.application.port.out;

import com.catalogueservice.domain.model.ResourceAvailability;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AvailabilityPort {
    ResourceAvailability save(ResourceAvailability availability);

    Optional<ResourceAvailability> findById(UUID id);

    Optional<ResourceAvailability> findByResourceIdAndSlotId(UUID resourceId, UUID slotId);

    List<ResourceAvailability> findByResourceId(UUID resourceId);

    List<ResourceAvailability> findBySlotId(UUID slotId);

    void deleteById(UUID id);
}