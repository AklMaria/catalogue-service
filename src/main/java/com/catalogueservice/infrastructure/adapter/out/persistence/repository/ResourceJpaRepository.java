package com.catalogueservice.infrastructure.adapter.out.persistence.repository;

import com.catalogueservice.infrastructure.adapter.out.persistence.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResourceJpaRepository extends JpaRepository<ResourceEntity, UUID> {}
