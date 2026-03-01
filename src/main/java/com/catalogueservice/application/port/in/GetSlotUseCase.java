package com.catalogueservice.application.port.in;

import com.catalogueservice.domain.model.Slot;

import java.util.Optional;
import java.util.UUID;

public interface GetSlotUseCase {
    Optional<Slot> getById(UUID id);
}