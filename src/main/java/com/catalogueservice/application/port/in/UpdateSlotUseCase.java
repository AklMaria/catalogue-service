package com.catalogueservice.application.port.in;

import com.catalogueservice.domain.model.Slot;

import java.util.Optional;
import java.util.UUID;

public interface UpdateSlotUseCase {
    Optional<Slot> update(UUID id, UpdateSlotCommand command);
}