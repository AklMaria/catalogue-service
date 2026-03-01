package com.catalogueservice.infrastructure.adapter.in.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitCatalogueConfig {

    public static final String EXCHANGE = "booking.exchange";

    public static final String BOOKING_REQUESTED_QUEUE = "booking.requested.queue";
    public static final String BOOKING_REQUESTED_KEY = "booking.requested";

    public static final String BOOKING_RESULT_QUEUE = "booking.result.queue";
    public static final String BOOKING_RESULT_KEY = "booking.result";


    public static final String BOOKING_CANCEL_REQUESTED_QUEUE = "booking.cancel.requested.queue";
    public static final String BOOKING_CANCEL_REQUESTED_KEY = "booking.cancel.requested";

    public static final String BOOKING_CANCEL_RESULT_QUEUE = "booking.cancel.result.queue";
    public static final String BOOKING_CANCEL_RESULT_KEY = "booking.cancel.result";

    @Bean
    public TopicExchange bookingExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue bookingRequestedQueue() {
        return QueueBuilder.durable(BOOKING_REQUESTED_QUEUE).build();
    }

    @Bean
    public Queue bookingResultQueue() {
        return QueueBuilder.durable(BOOKING_RESULT_QUEUE).build();
    }

    @Bean
    public Binding bookingRequestedBinding(TopicExchange bookingExchange, Queue bookingRequestedQueue) {
        return BindingBuilder.bind(bookingRequestedQueue).to(bookingExchange).with(BOOKING_REQUESTED_KEY);
    }

    @Bean
    public Binding bookingResultBinding(TopicExchange bookingExchange, Queue bookingResultQueue) {
        return BindingBuilder.bind(bookingResultQueue).to(bookingExchange).with(BOOKING_RESULT_KEY);
    }

    @Bean
    public Queue bookingCancelRequestedQueue() {
        return QueueBuilder.durable(BOOKING_CANCEL_REQUESTED_QUEUE).build();
    }

    @Bean
    public Queue bookingCancelResultQueue() {
        return QueueBuilder.durable(BOOKING_CANCEL_RESULT_QUEUE).build();
    }

    @Bean
    public Binding bookingCancelRequestedBinding(TopicExchange bookingExchange, Queue bookingCancelRequestedQueue) {
        return BindingBuilder.bind(bookingCancelRequestedQueue).to(bookingExchange).with(BOOKING_CANCEL_REQUESTED_KEY);
    }

    @Bean
    public Binding bookingCancelResultBinding(TopicExchange bookingExchange, Queue bookingCancelResultQueue) {
        return BindingBuilder.bind(bookingCancelResultQueue).to(bookingExchange).with(BOOKING_CANCEL_RESULT_KEY);
    }
}