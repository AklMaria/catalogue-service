package com.catalogueservice.infrastructure.adapter.out.persistence.repository;

import com.catalogueservice.infrastructure.adapter.out.persistence.entity.ResourceAvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AvailabilityJpaRepository extends JpaRepository<ResourceAvailabilityEntity, UUID> {
    Optional<ResourceAvailabilityEntity> findByResourceIdAndSlotId(UUID resourceId, UUID slotId);
    List<ResourceAvailabilityEntity> findByResourceId(UUID resourceId);
    List<ResourceAvailabilityEntity> findBySlotId(UUID slotId);

    @Modifying
    @Query("""
    update ResourceAvailabilityEntity ra
       set ra.consumed = ra.consumed + 1
     where ra.id = :id
       and ra.consumed + 1 <= ra.capacity
""")
    int tryConsumeOne(@Param("id") UUID id);


    @Modifying
    @Query("""
    update ResourceAvailabilityEntity ra
       set ra.consumed = ra.consumed - 1
     where ra.id = :id
       and ra.consumed > 0
""")
    int tryReleaseOne(@Param("id") UUID id);


}