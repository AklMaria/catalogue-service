package com.catalogueservice.infrastructure.adapter.out.persistence.repository;

import com.catalogueservice.infrastructure.adapter.out.persistence.entity.SlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SlotJpaRepository extends JpaRepository<SlotEntity, UUID> {}