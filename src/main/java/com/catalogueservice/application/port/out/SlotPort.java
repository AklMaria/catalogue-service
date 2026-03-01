package com.catalogueservice.application.port.out;

import com.catalogueservice.domain.model.Slot;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SlotPort {
    Slot save(Slot slot);
    List<Slot> findAll();
    Optional<Slot> findById(UUID id);
    void deleteById(UUID id);
}