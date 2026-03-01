package com.catalogueservice.infrastructure.adapter.out.persistence.mapper;

import com.catalogueservice.infrastructure.adapter.out.persistence.entity.ResourceEntity;
import com.catalogueservice.domain.model.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourcePersistenceMapper {

    public ResourceEntity toEntity(Resource r) {
        ResourceEntity e = new ResourceEntity();
        e.setId(r.getId());
        e.setName(r.getName());
        e.setDescription(r.getDescription());
        return e;
    }

    public Resource toDomain(ResourceEntity e) {
        return Resource.restore(e.getId(), e.getName(), e.getDescription());
    }
}
