package com.tserashkevich.e2etests.steps;

import com.tserashkevich.e2etests.DataStepsClass;
import com.tserashkevich.e2etests.dtos.RatingResponse;
import com.tserashkevich.e2etests.utils.TestUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RatingSteps extends DataStepsClass {
    @When("Send find all request to rating-service with sourceId and rideId")
    public void sendFindAllRequestToRatingServiceWithSourceIdAndRideId() {
        Response response = given()
                .baseUri(TestUtil.BASE_URL + TestUtil.RATINGS_PORT)
                .contentType(ContentType.JSON)
                .param("rideId", rideResponse.getId())
                .param("sourceId", rideResponse.getPassengerId())
                .when()
                .get(TestUtil.RATINGS_PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        ratingResponse = response.jsonPath()
                .getList("objectList", RatingResponse.class)
                .get(0);
    }

    @Then("RatingResponse should not be null")
    public void ratingResponseShouldNotBeNull() {
        assertThat(ratingResponse).isNotNull();
    }

    @And("RatingResponse should contains rating {string}")
    public void ratingResponseShouldContainsRating(String rating) {
        assertThat(ratingResponse.getRating())
                .isEqualTo(Integer.parseInt(rating));
    }
}
