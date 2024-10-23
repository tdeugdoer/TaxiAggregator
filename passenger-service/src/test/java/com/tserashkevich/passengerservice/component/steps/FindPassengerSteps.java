package com.tserashkevich.passengerservice.component.steps;

import com.tserashkevich.passengerservice.component.PassengerComponentTest;
import com.tserashkevich.passengerservice.dtos.ExceptionResponse;
import com.tserashkevich.passengerservice.dtos.PassengerResponse;
import com.tserashkevich.passengerservice.utils.TestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FindPassengerSteps extends PassengerComponentTest {
    @LocalServerPort
    private Integer port;
    private Response response;

    @When("Find existing passenger with ID {string}")
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

    @Then("PassengerResponse should contains passenger with ID {string}")
    public void passengerResponseShouldContainsPassengerWithId(String id) {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(PassengerResponse.class).getId())
                .isEqualTo(UUID.fromString(id));
    }

    @When("Find non existing passenger with ID {string}")
    public void passengerWithIdNotExists(String id) {
        response = given()
                .port(port)
                .pathParam("id", id)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Then("Find should be thrown PassengerNotFoundException")
    public void passengerNotFoundExceptionThrownForId() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.as(ExceptionResponse.class).getMessage())
                .isEqualTo(TestUtil.PASSENGER_NOT_FOUND_MESSAGE);
    }
}
