package com.catalogueservice.application.port.in;

import com.catalogueservice.domain.model.Resource;

import java.util.List;

public interface ListResourcesUseCase {
    List<Resource> listAll();
}
