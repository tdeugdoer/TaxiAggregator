package com.tserashkevich.rideservice.component.steps;

import com.tserashkevich.rideservice.component.RideComponentTest;
import com.tserashkevich.rideservice.dtos.ExceptionResponse;
import com.tserashkevich.rideservice.utils.TestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteRideSteps extends RideComponentTest {
    @LocalServerPort
    private Integer port;
    private Response response;

    @When("Delete ride method is called with ID {string}")
    public void deleteByIdMethodIsCalledWithId(String id) {
        response = given()
                .port(port)
                .pathParam("id", id)
                .when()
                .delete(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Then("Should return No Content")
    public void rideResponseShouldContainsRideWithId() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Then("Delete should be thrown RideNotFoundException")
    public void rideNotFoundExceptionThrownForId() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.as(ExceptionResponse.class).getMessage())
                .isEqualTo(TestUtil.RIDE_NOT_FOUND_MESSAGE);
    }
}
