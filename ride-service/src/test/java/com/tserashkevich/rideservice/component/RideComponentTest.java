package com.tserashkevich.rideservice.component;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features")
@CucumberContextConfiguration
@EmbeddedKafka(partitions = 1, topics = {"create-rating-topic", "change-driver-status-topic"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RideComponentTest {
}