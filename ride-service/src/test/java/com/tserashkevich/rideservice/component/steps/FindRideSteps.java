package com.tserashkevich.rideservice.component.steps;

import com.tserashkevich.rideservice.component.RideComponentTest;
import com.tserashkevich.rideservice.dtos.ExceptionResponse;
import com.tserashkevich.rideservice.dtos.RideResponse;
import com.tserashkevich.rideservice.utils.TestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FindRideSteps extends RideComponentTest {
    @LocalServerPort
    private Integer port;
    private Response response;

    @When("Find existing ride with ID {string}")
    public void findByIdMethodIsCalledWithId(String id) {
        response = given()
                .port(port)
                .pathParam("id", id)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Then("RideResponse should contains ride with ID {string}")
    public void rideResponseShouldContainsRideWithId(String id) {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(RideResponse.class).getId())
                .isEqualTo(id);
    }

    @When("Find non existing ride with ID {string}")
    public void rideWithIdNotExists(String id) {
        response = given()
                .port(port)
                .pathParam("id", id)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Then("Find should be thrown RideNotFoundException")
    public void rideNotFoundExceptionThrownForId() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.as(ExceptionResponse.class).getMessage())
                .isEqualTo(TestUtil.RIDE_NOT_FOUND_MESSAGE);
    }
}
