package com.catalogueservice.infrastructure.adapter.out.persistence.mapper;

import com.catalogueservice.domain.model.Slot;
import com.catalogueservice.infrastructure.adapter.out.persistence.entity.SlotEntity;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class SlotPersistenceMapper {

    // Zona “default” usata quando ricostruiamo uno ZonedDateTime da OffsetDateTime (DB)
    private static final ZoneId DEFAULT_ZONE = ZoneId.of("UTC");

    public SlotEntity toEntity(Slot slot) {
        SlotEntity e = new SlotEntity();
        e.setId(slot.getId());
        e.setStartDate(slot.getStartDate().toOffsetDateTime());
        e.setEndDate(slot.getEndDate().toOffsetDateTime());
        return e;
    }

    public Slot toDomain(SlotEntity e) {
        return Slot.restore(
                e.getId(),
                e.getStartDate().atZoneSameInstant(DEFAULT_ZONE),
                e.getEndDate().atZoneSameInstant(DEFAULT_ZONE)
        );
    }
}