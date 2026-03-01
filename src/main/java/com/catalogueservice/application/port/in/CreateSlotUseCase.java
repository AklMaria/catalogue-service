package com.catalogueservice.application.port.in;

import com.catalogueservice.domain.model.Slot;

public interface CreateSlotUseCase {
    Slot create(CreateSlotCommand command);
}