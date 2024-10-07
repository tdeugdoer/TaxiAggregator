package com.tserashkevich.rideservice.kafka;

import com.tserashkevich.rideservice.kafka.kafkaDtos.ChangeDriverStatusEvent;
import com.tserashkevich.rideservice.utils.LogList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChangeDriverStatusProducer {
    private final KafkaTemplate<String, ChangeDriverStatusEvent> kafkaTemplate;
    @Value("${spring.kafka.producer.change-driver-status-topic.name}")
    private String topic;

    public void sendChangeStatusEvent(ChangeDriverStatusEvent changeDriverStatusEvent) {
        log.info(LogList.LOG_KAFKA_SEND_MESSAGE, changeDriverStatusEvent);
        kafkaTemplate.send(topic, changeDriverStatusEvent);
    }
}
