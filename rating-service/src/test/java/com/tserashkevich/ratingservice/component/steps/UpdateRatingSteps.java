package com.tserashkevich.ratingservice.component.steps;

import com.tserashkevich.ratingservice.component.RatingComponentTest;
import com.tserashkevich.ratingservice.dtos.ExceptionResponse;
import com.tserashkevich.ratingservice.dtos.RatingRequest;
import com.tserashkevich.ratingservice.dtos.RatingResponse;
import com.tserashkevich.ratingservice.utils.TestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UpdateRatingSteps extends RatingComponentTest {
    @LocalServerPort
    private Integer port;
    private Response response;

    @When("Update rating method is called with ID {string} and RatingRequest of sourceId {string} and rideId {string}")
    public void updateRatingMethodIsCalledWithRatingRequestOfSourceIdAndRideId(String id, String sourceId, String rideId) {
        RatingRequest request = TestUtil.getRatingRequest(sourceId, rideId);

        response = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(request)
                .when()
                .put(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Then("RatingResponse should contains rating with ID {string} and sourceId {string} and rideId {string}")
    public void ratingResponseShouldContainsRatingWithIdAndSourceIdAndTargetId(String id, String sourceId, String rideId) {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());

        RatingResponse ratingResponse = response.as(RatingResponse.class);

        assertThat(ratingResponse.getId())
                .isEqualTo(UUID.fromString(id));
        assertThat(ratingResponse.getSourceId())
                .isEqualTo(UUID.fromString(sourceId));
        assertThat(ratingResponse.getRideId())
                .isEqualTo(rideId);
    }

    @Then("Update ExceptionResponse should be thrown")
    public void ExceptionResponseShouldBeThrown() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());

        ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);

        assertThat(exceptionResponse.getMessage())
                .isEqualTo(TestUtil.RATING_EXIST_MESSAGE);
    }
}
