package com.catalogueservice.infrastructure.adapter.in.messaging;

import com.catalogueservice.infrastructure.adapter.in.messaging.dto.BookingCancelRequestedEvent;
import com.catalogueservice.infrastructure.adapter.in.messaging.dto.BookingCancelResultEvent;
import com.catalogueservice.infrastructure.adapter.out.persistence.repository.AvailabilityJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
public class BookingCancelRequestedListener {

    private final ObjectMapper objectMapper;
    private final AvailabilityJpaRepository availabilityJpaRepository;
    private final RabbitBookingCancelResultPublisher resultPublisher;

    public BookingCancelRequestedListener(
            ObjectMapper objectMapper,
            AvailabilityJpaRepository availabilityJpaRepository,
            RabbitBookingCancelResultPublisher resultPublisher
    ) {
        this.objectMapper = objectMapper;
        this.availabilityJpaRepository = availabilityJpaRepository;
        this.resultPublisher = resultPublisher;
    }

    @RabbitListener(queues = RabbitCatalogueConfig.BOOKING_CANCEL_REQUESTED_QUEUE)
    @Transactional
    public void onMessage(String json) {
        try {
            BookingCancelRequestedEvent req = objectMapper.readValue(json, BookingCancelRequestedEvent.class);

            // prova release atomica
            int updated = availabilityJpaRepository.tryReleaseOne(req.availabilityId());

            if (updated == 1) {
                resultPublisher.publish(new BookingCancelResultEvent(
                        req.bookingId(),
                        req.availabilityId(),
                        "CANCELLED",
                        null,
                        Instant.now()
                ));
            } else {
                // non c'era nulla da rilasciare (consumed già 0 o id non valido)
                resultPublisher.publish(new BookingCancelResultEvent(
                        req.bookingId(),
                        req.availabilityId(),
                        "FAILED",
                        "NOTHING_TO_RELEASE",
                        Instant.now()
                ));
            }
        } catch (Exception e) {
            resultPublisher.publish(new BookingCancelResultEvent(
                    null, null,
                    "FAILED",
                    "INTERNAL_ERROR",
                    Instant.now()
            ));
        }
    }
}