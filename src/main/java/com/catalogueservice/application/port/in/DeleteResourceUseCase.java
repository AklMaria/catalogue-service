package com.catalogueservice.application.port.in;

import java.util.UUID;

public interface DeleteResourceUseCase {
    void deleteById(UUID id);
}
