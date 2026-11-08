# TaxiAggregator

Микросервисный агрегатор такси на Java 21 и Spring Boot 3: пассажиры, водители и автомобили, поездки, рейтинги.

## Архитектура

| Сервис | Порт | Хранилище | Назначение |
|---|---:|---|---|
| `passenger-service` | 8081 | PostgreSQL | Профили пассажиров |
| `driver-service` | 8080 | PostgreSQL | Водители, автомобили, доступность |
| `ride-service` | 8082 | MongoDB | Поездки, адреса, расстояние, статусы |
| `rating-service` | 8083 | Cassandra | Оценки, отзывы, средний рейтинг |

- **Синхронно (OpenFeign):** пассажиры и водители получают средний рейтинг из `rating-service`; `ride-service` проверяет пассажира, водителя и авто, а также обращается к Geoapify для геокодирования и расчёта маршрута.
- **Асинхронно (Kafka):** `ride-service` публикует события в `create-rating-topic` (потребитель — `rating-service`) и `change-driver-status-topic` (потребитель — `driver-service`).
- **Безопасность:** Keycloak выдаёт JWT, сервисы проверяют его как OAuth2 Resource Server; роли `USER` и `ADMIN`.
- **Инфраструктура:** Docker Compose, Kubernetes, Prometheus + Grafana, ELK, Micrometer Tracing.

## Технологии

- **Платформа:** Java 21, Gradle 8.8 (многомодульная сборка), Spring Boot 3.3.3, Spring Cloud 2023.0.3.
- **API:** Spring MVC, springdoc-openapi 2.6.0 (Swagger UI), Jakarta Bean Validation, глобальная обработка ошибок через `@RestControllerAdvice`.
- **Данные:** Spring Data JPA/Hibernate + PostgreSQL, Liquibase, QueryDSL 5.1.0; Spring Data MongoDB; Spring Data Cassandra.
- **Маппинг:** MapStruct 1.5.5 для Entity ↔ DTO, Lombok для сокращения boilerplate.
- **Интеграции:** OpenFeign, Resilience4j (Retry, CircuitBreaker), Apache Kafka (KRaft, JSON, DLT), Geoapify API.
- **Безопасность:** Spring Security, OAuth2 Resource Server, Keycloak, JWT, `@PreAuthorize`, проброс токена через Feign.
- **Наблюдаемость:** Actuator, Micrometer, Prometheus, Grafana, Kafka Exporter, Micrometer Tracing Brave, Logstash Logback Encoder, Elasticsearch/Logstash/Kibana.
- **Тестирование:** JUnit 5, Mockito, AssertJ, RestAssured, Testcontainers, WireMock, Spring Cloud Contract, Cucumber.
- **Инфраструктура:** Docker (layered jar), Docker Compose, Kubernetes (Deployment, Service, StatefulSet, PVC, NGINX Ingress), Spring Profiles.
