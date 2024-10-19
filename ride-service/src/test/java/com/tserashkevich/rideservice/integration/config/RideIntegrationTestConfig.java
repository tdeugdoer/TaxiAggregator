package com.tserashkevich.rideservice.integration.config;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@WireMockTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RideIntegrationTestConfig {
    @Container
    @ServiceConnection
    protected static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
    @RegisterExtension
    protected static WireMockExtension passengerWireMock = WireMockExtension.extensionOptions()
            .options(WireMockConfiguration.wireMockConfig().dynamicPort())
            .build();
    @RegisterExtension
    protected static WireMockExtension carWireMock = WireMockExtension.extensionOptions()
            .options(WireMockConfiguration.wireMockConfig().dynamicPort())
            .build();
    @RegisterExtension
    protected static WireMockExtension driverWireMock = WireMockExtension.extensionOptions()
            .options(WireMockConfiguration.wireMockConfig().dynamicPort())
            .build();
    @RegisterExtension
    protected static WireMockExtension geoapifyWireMock = WireMockExtension.extensionOptions()
            .options(WireMockConfiguration.wireMockConfig().dynamicPort())
            .build();
    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @DynamicPropertySource
    public static void initKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.openfeign.client.config.passenger.url", passengerWireMock::baseUrl);
        registry.add("spring.cloud.openfeign.client.config.car.url", carWireMock::baseUrl);
        registry.add("spring.cloud.openfeign.client.config.driver.url", driverWireMock::baseUrl);
        registry.add("spring.cloud.openfeign.client.config.geoapify.url", geoapifyWireMock::baseUrl);
    }
}
