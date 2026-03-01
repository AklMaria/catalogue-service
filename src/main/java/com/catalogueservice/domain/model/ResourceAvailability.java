package com.catalogueservice.domain.model;

import java.util.UUID;

public class ResourceAvailability {
    private final UUID id;
    private final UUID resourceId;
    private final UUID slotId;
    private final int capacity;
    private final int consumed;

    private ResourceAvailability(UUID id, UUID resourceId, UUID slotId, int capacity, int consumed) {
        this.id = id;
        this.resourceId = resourceId;
        this.slotId = slotId;
        this.capacity = capacity;
        this.consumed = consumed;
    }

    public static ResourceAvailability create(UUID resourceId, UUID slotId, int capacity) {
        if (resourceId == null || slotId == null) throw new IllegalArgumentException("resourceId/slotId required");
        if (capacity < 0) throw new IllegalArgumentException("capacity must be >= 0");
        return new ResourceAvailability(UUID.randomUUID(), resourceId, slotId, capacity, 0);
    }

    public static ResourceAvailability restore(UUID id, UUID resourceId, UUID slotId, int capacity, int consumed) {
        return new ResourceAvailability(id, resourceId, slotId, capacity, consumed);
    }

    // update capacity only (consumed rimane gestito da altri MS)
    public ResourceAvailability updateCapacity(int newCapacity) {
        if (newCapacity < 0) throw new IllegalArgumentException("capacity must be >= 0");
        if (newCapacity < this.consumed) throw new IllegalArgumentException("capacity cannot be < consumed");
        return new ResourceAvailability(this.id, this.resourceId, this.slotId, newCapacity, this.consumed);
    }

    public UUID getId() { return id; }
    public UUID getResourceId() { return resourceId; }
    public UUID getSlotId() { return slotId; }
    public int getCapacity() { return capacity; }
    public int getConsumed() { return consumed; }
}