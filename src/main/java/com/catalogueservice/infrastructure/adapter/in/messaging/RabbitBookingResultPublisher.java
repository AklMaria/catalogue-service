package com.catalogueservice.infrastructure.adapter.in.messaging;

import com.catalogueservice.infrastructure.adapter.in.messaging.dto.BookingResultEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitBookingResultPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitBookingResultPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(BookingResultEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(
                    RabbitCatalogueConfig.EXCHANGE,
                    RabbitCatalogueConfig.BOOKING_RESULT_KEY,
                    json
            );
        } catch (Exception e) {
            throw new IllegalStateException("Failed to publish BookingResultEvent", e);
        }
    }
}