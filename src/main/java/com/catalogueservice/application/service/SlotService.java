package com.catalogueservice.application.service;

import com.catalogueservice.application.port.in.*;
import com.catalogueservice.application.port.out.SlotPort;
import com.catalogueservice.domain.model.Slot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SlotService implements
        CreateSlotUseCase,
        ListSlotsUseCase,
        GetSlotUseCase,
        DeleteSlotUseCase,
       UpdateSlotUseCase
{

    private final SlotPort slotPort;

    public SlotService(SlotPort slotPort) {
        this.slotPort = slotPort;
    }

    @Override
    public Slot create(CreateSlotCommand command) {
        Slot slot = Slot.create(command.startDate(), command.endDate());
        return slotPort.save(slot);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Slot> listAll() {
        return slotPort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Slot> getById(UUID id) {
        return slotPort.findById(id);
    }

    @Override
    public void deleteById(UUID id) {
        slotPort.deleteById(id);
    }
    @Override
    public Optional<Slot> update(UUID id, UpdateSlotCommand command) {
        return slotPort.findById(id)
                .map(existing -> existing.update(command.startDate(), command.endDate()))
                .map(slotPort::save);
    }
}