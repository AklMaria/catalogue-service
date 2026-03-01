package com.catalogueservice.infrastructure.adapter.in.web.mapper;

import com.catalogueservice.application.port.in.UpdateResourceCommand;
import com.catalogueservice.infrastructure.adapter.in.web.dto.CreateResourceRequest;
import com.catalogueservice.infrastructure.adapter.in.web.dto.ResourceResponse;
import com.catalogueservice.application.port.in.CreateResourceCommand;
import com.catalogueservice.domain.model.Resource;
import com.catalogueservice.infrastructure.adapter.in.web.dto.UpdateResourceRequest;
import org.springframework.stereotype.Component;

@Component
public class ResourceWebMapper {

    public CreateResourceCommand toCommand(CreateResourceRequest req) {
        return new CreateResourceCommand(req.getName(), req.getDescription());
    }

    public ResourceResponse toResponse(Resource r) {
        return new ResourceResponse(r.getId(), r.getName(), r.getDescription());
    }

    public UpdateResourceCommand toCommand(UpdateResourceRequest req) {
        return new UpdateResourceCommand(req.getName(), req.getDescription());
    }

}
