package com.catalogueservice.infrastructure.adapter.in.web.controller;

import com.catalogueservice.application.port.in.*;
import com.catalogueservice.infrastructure.adapter.in.web.dto.*;
import com.catalogueservice.infrastructure.adapter.in.web.mapper.AvailabilityWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AvailabilityController {

    private final CreateAvailabilityUseCase createAvailability;
    private final GetAvailabilityUseCase getAvailability;
    private final ListAvailabilityByResourceUseCase listByResource;
    private final ListAvailabilityBySlotUseCase listBySlot;
    private final UpdateAvailabilityCapacityUseCase updateCapacity;
    private final DeleteAvailabilityUseCase deleteAvailability;
    private final AvailabilityWebMapper mapper;

    public AvailabilityController(
            CreateAvailabilityUseCase createAvailability,
            GetAvailabilityUseCase getAvailability,
            ListAvailabilityByResourceUseCase listByResource,
            ListAvailabilityBySlotUseCase listBySlot,
            UpdateAvailabilityCapacityUseCase updateCapacity,
            DeleteAvailabilityUseCase deleteAvailability,
            AvailabilityWebMapper mapper
    ) {
        this.createAvailability = createAvailability;
        this.getAvailability = getAvailability;
        this.listByResource = listByResource;
        this.listBySlot = listBySlot;
        this.updateCapacity = updateCapacity;
        this.deleteAvailability = deleteAvailability;
        this.mapper = mapper;
    }

    @PostMapping("/availabilities")
    public ResponseEntity<AvailabilityResponse> create(@Valid @RequestBody CreateAvailabilityRequest request) {
        var created = createAvailability.create(mapper.toCommand(request));
        return ResponseEntity.ok(mapper.toResponse(created));
    }

    @GetMapping("/availabilities/{id}")
    public ResponseEntity<AvailabilityResponse> getById(@PathVariable UUID id) {
        return getAvailability.getById(id)
                .map(a -> ResponseEntity.ok(mapper.toResponse(a)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/resources/{resourceId}/availabilities")
    public ResponseEntity<List<AvailabilityResponse>> listForResource(@PathVariable UUID resourceId) {
        var list = listByResource.listByResource(resourceId).stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/slots/{slotId}/availabilities")
    public ResponseEntity<List<AvailabilityResponse>> listForSlot(@PathVariable UUID slotId) {
        var list = listBySlot.listBySlot(slotId).stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/availabilities/{id}")
    public ResponseEntity<AvailabilityResponse> updateCapacity(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAvailabilityCapacityRequest request
    ) {
        return updateCapacity.updateCapacity(id, mapper.toCommand(request))
                .map(a -> ResponseEntity.ok(mapper.toResponse(a)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/availabilities/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteAvailability.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}