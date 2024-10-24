package com.tserashkevich.ratingservice.integration.config;

import com.datastax.oss.driver.api.core.CqlSession;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.InetSocketAddress;

@RequiredArgsConstructor
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class RatingIntegrationTestConfig {
    protected static final String KEYSPACE_NAME = "mykeyspace";
    @Container
    protected static CassandraContainer<?> cassandraContainer = new CassandraContainer<>("cassandra:latest");

    @DynamicPropertySource
    static void cassandraProperties(DynamicPropertyRegistry registry) {
        String contactPoint = cassandraContainer.getHost() + ":" + cassandraContainer.getMappedPort(9042);

        registry.add("spring.cassandra.contact-points", () -> contactPoint);
        registry.add("spring.cassandra.local-datacenter", cassandraContainer::getLocalDatacenter);
        registry.add("spring.cassandra.port", cassandraContainer::getFirstMappedPort);

        createKeyspace();
    }

    private static void createKeyspace() {
        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(cassandraContainer.getHost(), cassandraContainer.getMappedPort(9042)))
                .withLocalDatacenter(cassandraContainer.getLocalDatacenter())
                .build()) {
            session.execute(String.format(
                    "CREATE KEYSPACE IF NOT EXISTS %s WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};",
                    KEYSPACE_NAME));
        }
    }
}