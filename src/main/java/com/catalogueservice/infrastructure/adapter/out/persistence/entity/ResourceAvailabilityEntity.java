package com.catalogueservice.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;



@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "resource_availability",
        uniqueConstraints = @UniqueConstraint(name = "uq_resource_slot", columnNames = {"resource_id", "slot_id"})
)
public class ResourceAvailabilityEntity {

    @Id
    private UUID id;

    @Column(name = "resource_id", nullable = false)
    private UUID resourceId;

    @Column(name = "slot_id", nullable = false)
    private UUID slotId;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int consumed;






}