package com.catalogueservice.infrastructure.adapter.out.persistence.adapter;

import com.catalogueservice.infrastructure.adapter.out.persistence.mapper.ResourcePersistenceMapper;
import com.catalogueservice.infrastructure.adapter.out.persistence.repository.ResourceJpaRepository;
import com.catalogueservice.application.port.out.ResourcePort;
import com.catalogueservice.domain.model.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ResourcePersistenceAdapter implements ResourcePort {

    private final ResourceJpaRepository repo;
    private final ResourcePersistenceMapper mapper;

    public ResourcePersistenceAdapter(ResourceJpaRepository repo, ResourcePersistenceMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public Resource save(Resource resource) {
        var saved = repo.save(mapper.toEntity(resource));
        return mapper.toDomain(saved);
    }
    @Override
    public List<Resource> findAll() {
        return repo.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Resource> findById(UUID id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }
}
