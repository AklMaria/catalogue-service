package com.catalogueservice.application.port.out;

import com.catalogueservice.domain.model.Resource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResourcePort {
    Resource save(Resource resource);
    List<Resource> findAll();

    Optional<Resource> findById(UUID id);

    void deleteById(UUID id);
}
