package com.catalogueservice.infrastructure.adapter.in.web.mapper;

import com.catalogueservice.application.port.in.CreateAvailabilityCommand;
import com.catalogueservice.application.port.in.UpdateAvailabilityCapacityCommand;
import com.catalogueservice.domain.model.ResourceAvailability;
import com.catalogueservice.infrastructure.adapter.in.web.dto.*;

import org.springframework.stereotype.Component;

@Component
public class AvailabilityWebMapper {

    public CreateAvailabilityCommand toCommand(CreateAvailabilityRequest req) {
        return new CreateAvailabilityCommand(req.getResourceId(), req.getSlotId(), req.getCapacity());
    }

    public UpdateAvailabilityCapacityCommand toCommand(UpdateAvailabilityCapacityRequest req) {
        return new UpdateAvailabilityCapacityCommand(req.getCapacity());
    }

    public AvailabilityResponse toResponse(ResourceAvailability ra) {
        return new AvailabilityResponse(
                ra.getId(),
                ra.getResourceId(),
                ra.getSlotId(),
                ra.getCapacity(),
                ra.getConsumed()
        );
    }
}