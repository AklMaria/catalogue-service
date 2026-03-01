package com.catalogueservice.infrastructure.adapter.out.persistence.adapter;

import com.catalogueservice.infrastructure.adapter.out.persistence.mapper.SlotPersistenceMapper;
import com.catalogueservice.infrastructure.adapter.out.persistence.repository.SlotJpaRepository;
import com.catalogueservice.application.port.out.SlotPort;
import com.catalogueservice.domain.model.Slot;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class SlotPersistenceAdapter implements SlotPort {

    private final SlotJpaRepository repo;
    private final SlotPersistenceMapper mapper;

    public SlotPersistenceAdapter(SlotJpaRepository repo, SlotPersistenceMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public Slot save(Slot slot) {
        var saved = repo.save(mapper.toEntity(slot));
        return mapper.toDomain(saved);
    }

    @Override
    public List<Slot> findAll() {
        return repo.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Slot> findById(UUID id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }
}