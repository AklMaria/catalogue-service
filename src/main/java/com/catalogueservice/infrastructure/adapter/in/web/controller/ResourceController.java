package com.catalogueservice.infrastructure.adapter.in.web.controller;

import com.catalogueservice.application.port.in.*;
import com.catalogueservice.infrastructure.adapter.in.web.dto.CreateResourceRequest;
import com.catalogueservice.infrastructure.adapter.in.web.dto.ResourceResponse;
import com.catalogueservice.infrastructure.adapter.in.web.dto.UpdateResourceRequest;
import com.catalogueservice.infrastructure.adapter.in.web.mapper.ResourceWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final CreateResourceUseCase createResource;
    private final ListResourcesUseCase listResources;
    private final GetResourceUseCase getResource;
    private final DeleteResourceUseCase deleteResource;
    private final UpdateResourceUseCase updateResource;

    private final ResourceWebMapper mapper;


    public ResourceController(
            CreateResourceUseCase createResource,
            ListResourcesUseCase listResources,
            GetResourceUseCase getResource,
            DeleteResourceUseCase deleteResource,
            UpdateResourceUseCase updateResource,
            ResourceWebMapper mapper
    ) {
        this.createResource = createResource;
        this.listResources = listResources;
        this.getResource = getResource;
        this.deleteResource = deleteResource;
        this.updateResource = updateResource;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ResourceResponse> create(@Valid @RequestBody CreateResourceRequest request) {
        var created = createResource.create(mapper.toCommand(request));
        return ResponseEntity.ok(mapper.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<ResourceResponse>> listAll() {
        var list = listResources.listAll().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponse> getById(@PathVariable UUID id) {
        return getResource.getById(id)
                .map(r -> ResponseEntity.ok(mapper.toResponse(r)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteResource.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateResourceRequest request
    ) {
        return updateResource.update(id, mapper.toCommand(request))
                .map(r -> ResponseEntity.ok(mapper.toResponse(r)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
