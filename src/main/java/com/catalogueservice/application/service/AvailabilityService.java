package com.catalogueservice.application.service;

import com.catalogueservice.application.port.in.*;
import com.catalogueservice.application.port.out.AvailabilityPort;
import com.catalogueservice.application.port.out.ResourcePort;
import com.catalogueservice.application.port.out.SlotPort;
import com.catalogueservice.domain.model.ResourceAvailability;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AvailabilityService implements
        CreateAvailabilityUseCase,
        GetAvailabilityUseCase,
        ListAvailabilityByResourceUseCase,
        ListAvailabilityBySlotUseCase,
        UpdateAvailabilityCapacityUseCase,
        DeleteAvailabilityUseCase {

    private final AvailabilityPort availabilityPort;
    private final ResourcePort resourcePort;
    private final SlotPort slotPort;

    public AvailabilityService(AvailabilityPort availabilityPort, ResourcePort resourcePort, SlotPort slotPort) {
        this.availabilityPort = availabilityPort;
        this.resourcePort = resourcePort;
        this.slotPort = slotPort;
    }

    @Override
    public ResourceAvailability create(CreateAvailabilityCommand command) {
        // check esistenza FK (catalogue deve essere consistente)
        resourcePort.findById(command.resourceId())
                .orElseThrow(() -> new IllegalArgumentException("Resource not found"));
        slotPort.findById(command.slotId())
                .orElseThrow(() -> new IllegalArgumentException("Slot not found"));

        availabilityPort.findByResourceIdAndSlotId(command.resourceId(), command.slotId())
                .ifPresent(a -> { throw new IllegalArgumentException("Availability already exists for resource+slot"); });

        ResourceAvailability ra = ResourceAvailability.create(command.resourceId(), command.slotId(), command.capacity());
        return availabilityPort.save(ra);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResourceAvailability> getById(UUID id) {
        return availabilityPort.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceAvailability> listByResource(UUID resourceId) {
        return availabilityPort.findByResourceId(resourceId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceAvailability> listBySlot(UUID slotId) {
        return availabilityPort.findBySlotId(slotId);
    }

    @Override
    public Optional<ResourceAvailability> updateCapacity(UUID id, UpdateAvailabilityCapacityCommand command) {
        return availabilityPort.findById(id)
                .map(existing -> existing.updateCapacity(command.capacity()))
                .map(availabilityPort::save);
    }

    @Override
    public void deleteById(UUID id) {
        availabilityPort.deleteById(id);
    }
}