package com.tserashkevich.ratingservice.kafka;

import com.tserashkevich.ratingservice.kafka.kafkaDtos.RatingCreateEvent;
import com.tserashkevich.ratingservice.service.RatingService;
import com.tserashkevich.ratingservice.utils.LogList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CreateRatingConsumer {
    private final RatingService ratingService;

    @KafkaListener(topics = "${spring.kafka.consumer.create-rating-topic.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void handle(RatingCreateEvent ratingCreateEvent){
        log.info(LogList.RECEIVED_EVENT, ratingCreateEvent);
        ratingService.create(ratingCreateEvent);
    }
}
