package com.catalogueservice.infrastructure.adapter.out.persistence.adapter;

import com.catalogueservice.application.port.out.AvailabilityPort;
import com.catalogueservice.domain.model.ResourceAvailability;
import com.catalogueservice.infrastructure.adapter.out.persistence.mapper.AvailabilityPersistenceMapper;
import com.catalogueservice.infrastructure.adapter.out.persistence.repository.AvailabilityJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AvailabilityPersistenceAdapter implements AvailabilityPort {

    private final AvailabilityJpaRepository repo;
    private final AvailabilityPersistenceMapper mapper;

    public AvailabilityPersistenceAdapter(AvailabilityJpaRepository repo, AvailabilityPersistenceMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public ResourceAvailability save(ResourceAvailability availability) {
        var saved = repo.save(mapper.toEntity(availability));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ResourceAvailability> findById(UUID id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<ResourceAvailability> findByResourceIdAndSlotId(UUID resourceId, UUID slotId) {
        return repo.findByResourceIdAndSlotId(resourceId, slotId).map(mapper::toDomain);
    }

    @Override
    public List<ResourceAvailability> findByResourceId(UUID resourceId) {
        return repo.findByResourceId(resourceId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<ResourceAvailability> findBySlotId(UUID slotId) {
        return repo.findBySlotId(slotId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }
}