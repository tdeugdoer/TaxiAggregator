package com.tserashkevich.driverservice.component.steps;

import com.tserashkevich.driverservice.component.DriverAndCarComponentTest;
import com.tserashkevich.driverservice.dtos.ExceptionResponse;
import com.tserashkevich.driverservice.utils.CarTestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteCarSteps extends DriverAndCarComponentTest {
    @LocalServerPort
    private Integer port;
    private Response response;

    @When("Delete car method is called with ID {string}")
    public void deleteByIdMethodIsCalledWithId(String id) {
        response = given()
                .port(port)
                .pathParam("id", id)
                .when()
                .delete(CarTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Then("Car should return No Content")
    public void carResponseShouldContainsCarWithId() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Then("Delete should be thrown CarNotFoundException")
    public void carNotFoundExceptionThrownForId() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.as(ExceptionResponse.class).getMessage())
                .isEqualTo(CarTestUtil.CAR_NOT_FOUND_MESSAGE);
    }
}
