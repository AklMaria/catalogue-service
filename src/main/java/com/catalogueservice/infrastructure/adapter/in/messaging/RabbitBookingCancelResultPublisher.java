package com.catalogueservice.infrastructure.adapter.in.messaging;

import com.catalogueservice.infrastructure.adapter.in.messaging.dto.BookingCancelResultEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitBookingCancelResultPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitBookingCancelResultPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(BookingCancelResultEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(
                    RabbitCatalogueConfig.EXCHANGE,
                    RabbitCatalogueConfig.BOOKING_CANCEL_RESULT_KEY,
                    json
            );
        } catch (Exception e) {
            throw new IllegalStateException("Failed to publish BookingCancelResultEvent", e);
        }
    }
}