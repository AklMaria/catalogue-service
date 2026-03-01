package com.catalogueservice.application.port.in;

import java.util.UUID;

public interface DeleteAvailabilityUseCase {
    void deleteById(UUID id);
}