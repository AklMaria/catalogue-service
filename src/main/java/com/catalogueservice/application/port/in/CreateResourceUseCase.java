package com.catalogueservice.application.port.in;

import com.catalogueservice.domain.model.Resource;

public interface CreateResourceUseCase {
    Resource create(CreateResourceCommand command);
}
