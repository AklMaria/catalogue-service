package com.catalogueservice.infrastructure.adapter.in.web.mapper;

import com.catalogueservice.application.port.in.CreateSlotCommand;
import com.catalogueservice.application.port.in.UpdateSlotCommand;
import com.catalogueservice.domain.model.Slot;
import com.catalogueservice.infrastructure.adapter.in.web.dto.CreateSlotRequest;
import com.catalogueservice.infrastructure.adapter.in.web.dto.SlotResponse;
import com.catalogueservice.infrastructure.adapter.in.web.dto.UpdateSlotRequest;
import org.springframework.stereotype.Component;

@Component
public class SlotWebMapper {

    public CreateSlotCommand toCommand(CreateSlotRequest req) {
        return new CreateSlotCommand(req.getStartDate(), req.getEndDate());
    }

    public SlotResponse toResponse(Slot s) {
        return new SlotResponse(s.getId(), s.getStartDate(), s.getEndDate());
    }

    public UpdateSlotCommand toCommand(UpdateSlotRequest req) {
        return new UpdateSlotCommand(req.getStartDate(), req.getEndDate());
    }


}