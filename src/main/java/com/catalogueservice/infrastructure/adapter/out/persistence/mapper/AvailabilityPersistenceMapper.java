package com.catalogueservice.infrastructure.adapter.out.persistence.mapper;

import com.catalogueservice.domain.model.ResourceAvailability;
import com.catalogueservice.infrastructure.adapter.out.persistence.entity.ResourceAvailabilityEntity;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityPersistenceMapper {

    public ResourceAvailabilityEntity toEntity(ResourceAvailability ra) {
        ResourceAvailabilityEntity e = new ResourceAvailabilityEntity();
        e.setId(ra.getId());
        e.setResourceId(ra.getResourceId());
        e.setSlotId(ra.getSlotId());
        e.setCapacity(ra.getCapacity());
        e.setConsumed(ra.getConsumed());
        return e;
    }

    public ResourceAvailability toDomain(ResourceAvailabilityEntity e) {
        return ResourceAvailability.restore(
                e.getId(),
                e.getResourceId(),
                e.getSlotId(),
                e.getCapacity(),
                e.getConsumed()
        );
    }
}