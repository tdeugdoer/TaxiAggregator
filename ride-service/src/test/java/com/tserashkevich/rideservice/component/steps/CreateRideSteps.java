package com.tserashkevich.rideservice.component.steps;

import com.tserashkevich.rideservice.component.RideComponentTest;
import com.tserashkevich.rideservice.dtos.CreateRideRequest;
import com.tserashkevich.rideservice.dtos.CreateRideResponse;
import com.tserashkevich.rideservice.dtos.ValidationErrorResponse;
import com.tserashkevich.rideservice.utils.TestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreateRideSteps extends RideComponentTest {
    @LocalServerPort
    private Integer port;
    private Response response;

    @When("Create ride with passengerId {string}")
    public void createRideMethodIsCalledWithRideRequestOfUsernameAndPhone(String passengerId) {
        CreateRideRequest request = TestUtil.getCreateRideRequest(passengerId);

        response = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestUtil.DEFAULT_PATH)
                .then()
                .extract()
                .response();
    }

    @Then("Create RideResponse should contains ride with passengerId {string}")
    public void rideResponseShouldContainsRideWithUsernameAndPhone(String passengerId) {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.CREATED.value());

        CreateRideResponse rideResponse = response.as(CreateRideResponse.class);

        assertThat(rideResponse.getPassengerId())
                .isEqualTo(UUID.fromString(passengerId));
    }

    @Then("ValidationErrorResponse should contains passengerId")
    public void ValidationErrorResponseShouldBeContainsPassengerId() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());

        ValidationErrorResponse validationErrorResponse = response.as(ValidationErrorResponse.class);

        assertThat(validationErrorResponse.getViolations().get(0).getFieldName())
                .isEqualTo("passengerId");
    }
}
