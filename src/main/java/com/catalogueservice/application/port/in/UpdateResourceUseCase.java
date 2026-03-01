package com.catalogueservice.application.port.in;

import com.catalogueservice.domain.model.Resource;

import java.util.Optional;
import java.util.UUID;

public interface UpdateResourceUseCase {
    Optional<Resource> update(UUID id, UpdateResourceCommand command);
}
