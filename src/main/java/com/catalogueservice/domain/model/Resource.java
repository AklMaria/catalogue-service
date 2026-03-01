package com.catalogueservice.domain.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Resource {
    private final UUID id;
    private final String name;
    private final String description;

    private Resource(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static Resource create(String name, String description) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name is required");
        return new Resource(UUID.randomUUID(), name.trim(), description);
    }

    public static Resource restore(UUID id, String name, String description) {
        return new Resource(id, name, description);
    }

    public Resource update(String name, String description) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name is required");
        return new Resource(this.id, name.trim(), description);
    }


}
