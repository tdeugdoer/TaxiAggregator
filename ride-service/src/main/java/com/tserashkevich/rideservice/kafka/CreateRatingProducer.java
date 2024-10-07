package com.tserashkevich.rideservice.kafka;

import com.tserashkevich.rideservice.kafka.kafkaDtos.RatingCreateEvent;
import com.tserashkevich.rideservice.utils.LogList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CreateRatingProducer {
    private final KafkaTemplate<String, RatingCreateEvent> kafkaTemplate;
    @Value("${spring.kafka.producer.create-rating-topic.name}")
    private String topic;

    public void sendRatingCreateEvent(RatingCreateEvent event) {
        log.info(LogList.LOG_KAFKA_SEND_MESSAGE, event);
        kafkaTemplate.send(topic, event);
    }
}
