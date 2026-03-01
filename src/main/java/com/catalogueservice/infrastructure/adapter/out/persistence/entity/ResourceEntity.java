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
@Table(name = "resources")
public class ResourceEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;


}
