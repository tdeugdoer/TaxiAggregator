package com.tserashkevich.passengerservice.component.steps;

import com.tserashkevich.passengerservice.component.PassengerComponentTest;
import com.tserashkevich.passengerservice.dtos.ExceptionResponse;
import com.tserashkevich.passengerservice.utils.TestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeletePassengerSteps extends PassengerComponentTest {
    @LocalServerPort
    private Integer port;
    private Response response;

    @When("Delete passenger method is called with ID {string}")
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
    public void passengerResponseShouldContainsPassengerWithId() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Then("Delete should be thrown PassengerNotFoundException")
    public void passengerNotFoundExceptionThrownForId() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.as(ExceptionResponse.class).getMessage())
                .isEqualTo(TestUtil.PASSENGER_NOT_FOUND_MESSAGE);
    }
}
