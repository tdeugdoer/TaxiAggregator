package com.tserashkevich.rideservice.configs.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.producer.create-rating-topic.name}")
    private String ratingProducerTopic;

    @Bean
    public NewTopic createRatingProducerTopic() {
        return TopicBuilder
                .name(ratingProducerTopic)
                .build();
    }
}
