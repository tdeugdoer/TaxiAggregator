package com.tserashkevich.ratingservice.integration;

import com.tserashkevich.ratingservice.dtos.*;
import com.tserashkevich.ratingservice.integration.config.RatingIntegrationTestConfig;
import com.tserashkevich.ratingservice.models.Rating;
import com.tserashkevich.ratingservice.utils.TestUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
public class RatingIntegrationTest extends RatingIntegrationTestConfig {
    @LocalServerPort
    private Integer port;

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @BeforeEach
    void setUp() {
        cassandraTemplate.getCqlOperations().execute("CREATE TABLE IF NOT EXISTS mykeyspace.ratings (" +
                "id UUID PRIMARY KEY," +
                "source_id UUID," +
                "target_id UUID," +
                "ride_id text," +
                "rating int," +
                "comment text," +
                "creation_time timestamp" +
                ");");
        cassandraTemplate.getCqlOperations().execute("CREATE INDEX IF NOT EXISTS ON mykeyspace.ratings (target_id);");
        cassandraTemplate.truncate(Rating.class);
        cassandraTemplate.insert(TestUtil.getRating());
        cassandraTemplate.insert(TestUtil.getSecondRating());
    }

    @Test
    void givenNonExistingRating_whenCreateRatingByRequest_thenCreateNewRating() {
        RatingRequest request = TestUtil.getNonExistingRatingRequest();
        RatingResponse response = TestUtil.getNonExistingRatingResponse();

        RatingResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestUtil.DEFAULT_PATH)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(RatingResponse.class);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "creationTime")
                .isEqualTo(response);
    }

    @Test
    void givenExistingRating_whenCreateRatingByRequest_thenReturnExceptionResponse() {
        RatingRequest request = TestUtil.getRatingRequest();

        ExceptionResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestUtil.DEFAULT_PATH)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(TestUtil.RATING_EXIST_MESSAGE);
    }

    @Test
    void givenExistingRating_whenEditRating_thenUpdateRating() {
        RatingRequest request = TestUtil.getNonExistingRatingRequest();
        RatingResponse response = TestUtil.getNonExistingRatingResponse();

        RatingResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", TestUtil.ID)
                .body(request)
                .when()
                .put(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RatingResponse.class);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(response);
    }

    @Test
    void givenNonExistingRating_whenEditRating_thenReturnExceptionResponse() {
        RatingRequest request = TestUtil.getNonExistingRatingRequest();

        ExceptionResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", TestUtil.NON_EXISTING_ID)
                .body(request)
                .when()
                .put(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(TestUtil.RATING_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenExistingRating_whenDeleteRating_thenDeleteRating() {
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", TestUtil.ID)
                .when()
                .delete(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void givenNonExistingRating_whenDeleteRating_thenReturnExceptionResponse() {
        ExceptionResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", TestUtil.NON_EXISTING_ID)
                .when()
                .delete(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(TestUtil.RATING_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenValidParams_whenFindRatings_thenReturnPageOfRatingResponse() {
        Map<String, Object> findAllParamsMap = TestUtil.getFindAllParamsMap();
        PageResponse<RatingResponse> expectedResponse = TestUtil.getPageResponse();

        Response result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .params(findAllParamsMap)
                .when()
                .get(TestUtil.DEFAULT_PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        List<RatingResponse> passengerResponses = result.jsonPath().getList("objectList", RatingResponse.class);

        assertThat(passengerResponses).hasSize(2);
        assertThat(passengerResponses).usingRecursiveComparison().isEqualTo(expectedResponse.getObjectList());
    }

    @Test
    void givenExistingRating_whenFindByIdRating_thenReturnRatingResponse() {
        RatingResponse response = TestUtil.getRatingResponse();

        RatingResponse result = given()
                .port(port)
                .pathParam("id", TestUtil.ID)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RatingResponse.class);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(response);
    }

    @Test
    void givenNonExistingRating_whenFindByIdRating_thenReturnExceptionResponse() {
        ExceptionResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", TestUtil.NON_EXISTING_ID)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(TestUtil.RATING_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenTargetId_whenFindAvgRating_thenReturnDouble() {
        Double result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("targetId", TestUtil.TARGET_ID)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/avg/{targetId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Double.class);

        assertThat(result).isEqualTo(Double.parseDouble(TestUtil.RATING.toString()));
    }

    @Test
    void givenRatingId_whenFindFeedbacks_thenReturnFeedbacks() {
        List<Feedback> response = TestUtil.getOneFeedbacks();

        Response result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("targetId", TestUtil.TARGET_ID)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/feedbacks/{targetId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        List<Feedback> feedbacks = result.jsonPath().getList("", Feedback.class);

        assertThat(feedbacks).usingRecursiveComparison().isEqualTo(response);
    }
}
