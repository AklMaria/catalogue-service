package com.catalogueservice.application.service;

import com.catalogueservice.application.port.in.*;
import com.catalogueservice.application.port.out.ResourcePort;
import com.catalogueservice.domain.model.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ResourceService implements
        CreateResourceUseCase,
        ListResourcesUseCase,
        GetResourceUseCase ,
        DeleteResourceUseCase,
        UpdateResourceUseCase{

    private final ResourcePort resourcePort;

    public ResourceService(ResourcePort resourcePort) {
        this.resourcePort = resourcePort;
    }

    @Override
    public Resource create(CreateResourceCommand command) {
        Resource resource = Resource.create(command.name(), command.description());
        return resourcePort.save(resource);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resource> listAll() {
        return resourcePort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Resource> getById(UUID id) {
        return resourcePort.findById(id);
    }

    @Override
    public void deleteById(UUID id) {
        resourcePort.deleteById(id);
    }
    @Override
    public Optional<Resource> update(UUID id, UpdateResourceCommand command) {
        return resourcePort.findById(id)
                .map(existing -> existing.update(command.name(), command.description()))
                .map(resourcePort::save);
    }


}
