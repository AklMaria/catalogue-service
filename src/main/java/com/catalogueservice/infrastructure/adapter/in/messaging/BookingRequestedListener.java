package com.catalogueservice.infrastructure.adapter.in.messaging;

import com.catalogueservice.application.port.out.AvailabilityPort;
import com.catalogueservice.application.port.out.ResourcePort;
import com.catalogueservice.application.port.out.SlotPort;
import com.catalogueservice.domain.model.Resource;
import com.catalogueservice.domain.model.Slot;
import com.catalogueservice.infrastructure.adapter.in.messaging.dto.BookingRequestedEvent;
import com.catalogueservice.infrastructure.adapter.in.messaging.dto.BookingResultEvent;
import com.catalogueservice.infrastructure.adapter.out.persistence.repository.AvailabilityJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
public class BookingRequestedListener {

    private final ObjectMapper objectMapper;
    private final AvailabilityJpaRepository availabilityJpaRepository;
    private final AvailabilityPort availabilityPort;
    private final ResourcePort resourcePort;
    private final SlotPort slotPort;
    private final RabbitBookingResultPublisher resultPublisher;

    public BookingRequestedListener(
            ObjectMapper objectMapper,
            AvailabilityJpaRepository availabilityJpaRepository,
            AvailabilityPort availabilityPort,
            ResourcePort resourcePort,
            SlotPort slotPort,
            RabbitBookingResultPublisher resultPublisher
    ) {
        this.objectMapper = objectMapper;
        this.availabilityJpaRepository = availabilityJpaRepository;
        this.availabilityPort = availabilityPort;
        this.resourcePort = resourcePort;
        this.slotPort = slotPort;
        this.resultPublisher = resultPublisher;
    }

    @RabbitListener(queues = RabbitCatalogueConfig.BOOKING_REQUESTED_QUEUE)
    @Transactional
    public void onMessage(String json) {
        BookingRequestedEvent req;
        try {
            req = objectMapper.readValue(json, BookingRequestedEvent.class);
        } catch (Exception e) {
            // messaggio malformato → fallimento generico
            resultPublisher.publish(new BookingResultEvent(
                    null, null,
                    "FAILED",
                    "INTERNAL_ERROR",
                    null, null,
                    Instant.now()
            ));
            return;
        }

        try {
            // 1) check availability esiste
            var availabilityOpt = availabilityPort.findById(req.availabilityId());
            if (availabilityOpt.isEmpty()) {
                resultPublisher.publish(new BookingResultEvent(
                        req.bookingId(),
                        req.availabilityId(),
                        "REJECTED",
                        "AVAILABILITY_NOT_FOUND",
                        null, null,
                        Instant.now()
                ));
                return;
            }

            // 2) consumo atomico: consumed = consumed + 1 se <= capacity
            int updated = availabilityJpaRepository.tryConsumeOne(req.availabilityId());
            if (updated == 0) {
                // capacity reached (o gara persa)
                resultPublisher.publish(new BookingResultEvent(
                        req.bookingId(),
                        req.availabilityId(),
                        "REJECTED",
                        "CAPACITY_REACHED",
                        null, null,
                        Instant.now()
                ));
                return;
            }

            // 3) confirmed → snapshot resource + slot
            var availability = availabilityOpt.get();

            Resource resource = resourcePort.findById(availability.getResourceId())
                    .orElseThrow(() -> new IllegalStateException("Resource not found for availability"));
            Slot slot = slotPort.findById(availability.getSlotId())
                    .orElseThrow(() -> new IllegalStateException("Slot not found for availability"));

            BookingResultEvent.ResourceSnapshot rSnap =
                    new BookingResultEvent.ResourceSnapshot(resource.getId(), resource.getName(), resource.getDescription());

            BookingResultEvent.SlotSnapshot sSnap =
                    new BookingResultEvent.SlotSnapshot(slot.getId(), slot.getStartDate(), slot.getEndDate());

            resultPublisher.publish(new BookingResultEvent(
                    req.bookingId(),
                    req.availabilityId(),
                    "CONFIRMED",
                    null,
                    rSnap,
                    sSnap,
                    Instant.now()
            ));

        } catch (Exception e) {
            resultPublisher.publish(new BookingResultEvent(
                    req.bookingId(),
                    req.availabilityId(),
                    "FAILED",
                    "INTERNAL_ERROR",
                    null, null,
                    Instant.now()
            ));
        }
    }
}