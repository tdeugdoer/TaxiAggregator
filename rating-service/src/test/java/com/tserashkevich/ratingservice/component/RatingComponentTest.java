package com.tserashkevich.ratingservice.component;

import com.datastax.oss.driver.api.core.CqlSession;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.UUID;

@Testcontainers
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features")
@CucumberContextConfiguration
@EmbeddedKafka(partitions = 1, topics = "create-rating-topic")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RatingComponentTest {
    protected static final String KEYSPACE_NAME = "mykeyspace";
    @Container
    protected static CassandraContainer<?> cassandraContainer = new CassandraContainer<>("cassandra:latest");

    @DynamicPropertySource
    static void cassandraProperties(DynamicPropertyRegistry registry) {
        cassandraContainer.start();

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
            session.execute("CREATE TABLE IF NOT EXISTS mykeyspace.ratings (" +
                    "id UUID PRIMARY KEY," +
                    "source_id UUID," +
                    "target_id UUID," +
                    "ride_id text," +
                    "rating int," +
                    "comment text," +
                    "creation_time timestamp" +
                    ");");
            session.execute("CREATE INDEX IF NOT EXISTS ON mykeyspace.ratings (target_id);");

            UUID id = UUID.fromString("6b51fbb1-2a9f-4978-8455-c9a555223946");
            UUID sourceId = UUID.fromString("11111111-1111-1111-1111-111111111111");
            UUID targetId = UUID.fromString("11111111-1111-1111-1111-111111111111");
            Instant creationTime = Instant.parse("2000-01-01T00:00:00Z");

            session.execute("INSERT INTO mykeyspace.ratings (id, source_id, target_id, ride_id, rating, comment, creation_time)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)", id, sourceId, targetId, "507f1f77bcf86cd799439011", 3, "Comment", creationTime);
        }
    }
}