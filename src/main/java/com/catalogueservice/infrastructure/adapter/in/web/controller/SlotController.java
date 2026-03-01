package com.catalogueservice.infrastructure.adapter.in.web.controller;

import com.catalogueservice.application.port.in.*;
import com.catalogueservice.infrastructure.adapter.in.web.dto.CreateSlotRequest;
import com.catalogueservice.infrastructure.adapter.in.web.dto.SlotResponse;
import com.catalogueservice.infrastructure.adapter.in.web.dto.UpdateSlotRequest;
import com.catalogueservice.infrastructure.adapter.in.web.mapper.SlotWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/slots")
public class SlotController {

    private final CreateSlotUseCase createSlot;
    private final ListSlotsUseCase listSlots;
    private final GetSlotUseCase getSlot;
    private final DeleteSlotUseCase deleteSlot;
    private final SlotWebMapper mapper;
    private final UpdateSlotUseCase updateSlot;

    public SlotController(
            CreateSlotUseCase createSlot,
            ListSlotsUseCase listSlots,
            GetSlotUseCase getSlot,
            DeleteSlotUseCase deleteSlot,
            SlotWebMapper mapper,
            UpdateSlotUseCase updateSlot
    ) {
        this.createSlot = createSlot;
        this.listSlots = listSlots;
        this.getSlot = getSlot;
        this.deleteSlot = deleteSlot;
        this.updateSlot = updateSlot;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<SlotResponse> create(@Valid @RequestBody CreateSlotRequest request) {
        var created = createSlot.create(mapper.toCommand(request));
        return ResponseEntity.ok(mapper.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<SlotResponse>> listAll() {
        var list = listSlots.listAll().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlotResponse> getById(@PathVariable UUID id) {
        return getSlot.getById(id)
                .map(s -> ResponseEntity.ok(mapper.toResponse(s)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteSlot.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SlotResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateSlotRequest request
    ) {
        return updateSlot.update(id, mapper.toCommand(request))
                .map(s -> ResponseEntity.ok(mapper.toResponse(s)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}