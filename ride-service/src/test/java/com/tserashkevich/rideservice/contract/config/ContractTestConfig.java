package com.tserashkevich.rideservice.contract.config;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WireMockTest
@EmbeddedKafka(partitions = 1, topics = {"create-rating-topic", "change-driver-status-topic"})
@Testcontainers
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = {"com.tserashkevich:passenger-service:+:8081",
                "com.tserashkevich:driver-service:+:8080"})
@ActiveProfiles("test")
public abstract class ContractTestConfig {
    @Container
    @ServiceConnection
    protected static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @RegisterExtension
    protected static WireMockExtension geoapifyWireMock = WireMockExtension.extensionOptions()
            .options(WireMockConfiguration.wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.openfeign.client.config.geoapify.url", geoapifyWireMock::baseUrl);
    }
}
