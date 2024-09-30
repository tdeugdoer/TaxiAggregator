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
    @Value("${spring.kafka.producer.change-driver-status-topic.name}")
    private String driverProducerTopic;

    @Bean
    public NewTopic createRatingProducerTopic() {
        return TopicBuilder
                .name(ratingProducerTopic)
                .build();
    }

    @Bean
    public NewTopic createDriverProducerTopic() {
        return TopicBuilder
                .name(driverProducerTopic)
                .build();
    }
}
