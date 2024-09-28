package com.tserashkevich.rideservice.kafka;

import com.tserashkevich.rideservice.kafka.kafkaDtos.RatingCreateEvent;
import com.tserashkevich.rideservice.kafka.kafkaDtos.SimpleEvent;
import com.tserashkevich.rideservice.utils.LogList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CreateRatingProducer {
    @Value("${spring.kafka.producer.create-rating-topic.name}")
    private String topic;
    private final KafkaTemplate<String, RatingCreateEvent> kafkaTemplate;

    public void sendRatingCreateEvent(RatingCreateEvent event) {
        log.info(LogList.LOG_KAFKA_SEND_MESSAGE, event);
        Message<SimpleEvent> message = MessageBuilder
                .withPayload(new SimpleEvent("name"))
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
        kafkaTemplate.send(message);
    }
}
