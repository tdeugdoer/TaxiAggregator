package com.tserashkevich.e2etests.steps;

import com.tserashkevich.e2etests.DataStepsClass;
import com.tserashkevich.e2etests.dtos.*;
import com.tserashkevich.e2etests.utils.TestUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RideSteps extends DataStepsClass {
    private CreateRideRequest createRideRequest;
    private CreateRatingRequest createRatingRequest;
    private ExceptionResponse exceptionResponse;

    @Given("CreateRideRequest with startGeoPoint {string} and endGeoPoint {string}")
    public void createRideRequestWithStartGeoPointAndEndGeoPoint(String startGeoPoint, String endGeoPoint) {
        createRideRequest = TestUtil.getCreateRideRequest(passengerResponse.getId().toString(), startGeoPoint, endGeoPoint);
    }

    @When("Send create request to ride-service with createRideRequest")
    public void sendCreateRequestToRideServiceWithCreateRideRequest() {
        createRideResponse = given()
                .baseUri(TestUtil.BASE_URL + TestUtil.RIDES_PORT)
                .contentType(ContentType.JSON)
                .body(createRideRequest)
                .when()
                .post(TestUtil.RIDES_PATH)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CreateRideResponse.class);
    }

    @Then("CreateRideResponse should not be null")
    public void createRideResponseShouldNotBeNull() {
        assertThat(createRideResponse).isNotNull();
    }

    @And("CreateRideResponse should contains startGeoPoint {string}")
    public void createRideResponseShouldContainsStartGeoPoint(String startGeoPoint) {
        assertThat(createRideResponse.getStartAddress().getGeoPoint()).isEqualTo(startGeoPoint);
    }

    @And("CreateRideResponse should contains endGeoPoint {string}")
    public void createRideResponseShouldContainsEndGeoPoint(String endGeoPoint) {
        assertThat(createRideResponse.getEndAddress().getGeoPoint()).isEqualTo(endGeoPoint);
    }

    @When("Send add driver request to ride-service with rideId and driverId")
    public void sendAddDriverRequestToRideServiceWithRideIdAndDriverId() {
        rideResponse = given()
                .baseUri(TestUtil.BASE_URL + TestUtil.RIDES_PORT)
                .contentType(ContentType.JSON)
                .pathParam("rideId", createRideResponse.getId())
                .pathParam("driverId", driverResponse.getId())
                .when()
                .patch(TestUtil.RIDES_PATH + "/changeDriver/{rideId}/{driverId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RideResponse.class);
    }

    @Then("RideResponse should not be null")
    public void rideResponseShouldNotBeNull() {
        assertThat(rideResponse).isNotNull();
    }

    @And("RideResponse should contains the sent driverId")
    public void rideResponseShouldContainsTheSentDriverId() {
        assertThat(rideResponse.getDriverId()).isEqualTo(driverResponse.getId());
    }

    @Given("CreateRatingRequest with comment {string} and rating {string}")
    public void createRatingRequestWithCommentAndRating(String comment, String rating) {
        createRatingRequest = TestUtil.getCreateRatingRequest(rideResponse.getId(), comment, rating);
    }

    @When("Send bad create comment request to ride-service with createRatingRequest")
    public void sendBadCreateCommentRequestToRideServiceWithCreateRatingRequest() {
        exceptionResponse = given()
                .baseUri(TestUtil.BASE_URL + TestUtil.RIDES_PORT)
                .contentType(ContentType.JSON)
                .body(createRatingRequest)
                .when()
                .post(TestUtil.RIDES_PATH + "/passengerComment")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .as(ExceptionResponse.class);
    }

    @Then("ExceptionResponse should contains RideNotFinishedException")
    public void exceptionResponseShouldContainsRideNotFinishedException() {
        assertThat(exceptionResponse.getMessage()).isEqualTo(TestUtil.RIDE_NOT_FINISHED_MESSAGE);
    }

    @When("Send change status request to ride-service with rideId and status {string}")
    public void sendChangeStatusRequestToRideServiceWithRideIdAndStatus(String status) {
        rideResponse = given()
                .baseUri(TestUtil.BASE_URL + TestUtil.RIDES_PORT)
                .contentType(ContentType.JSON)
                .pathParam("rideId", rideResponse.getId())
                .pathParam("status", status)
                .when()
                .patch(TestUtil.RIDES_PATH + "/changeStatus/{rideId}/{status}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RideResponse.class);
    }

    @And("RideResponse should contains status {string}")
    public void rideResponseShouldContainsStatus(String status) {
        assertThat(rideResponse.getStatus()).isEqualTo(status);
    }

    @When("Send create comment request to ride-service with createRatingRequest")
    public void sendCreateCommentRequestToRideServiceWithCreateRatingRequest() {
        given()
                .baseUri(TestUtil.BASE_URL + TestUtil.RIDES_PORT)
                .contentType(ContentType.JSON)
                .body(createRatingRequest)
                .when()
                .post(TestUtil.RIDES_PATH + "/passengerComment")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
