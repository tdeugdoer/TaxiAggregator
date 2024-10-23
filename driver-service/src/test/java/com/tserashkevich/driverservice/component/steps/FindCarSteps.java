package com.tserashkevich.driverservice.component.steps;

import com.tserashkevich.driverservice.component.DriverAndCarComponentTest;
import com.tserashkevich.driverservice.dtos.CarResponse;
import com.tserashkevich.driverservice.dtos.ExceptionResponse;
import com.tserashkevich.driverservice.utils.CarTestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FindCarSteps extends DriverAndCarComponentTest {
    @LocalServerPort
    private Integer port;
    private Response response;

    @When("Find existing car with ID {string}")
    public void findByIdMethodIsCalledWithId(String id) {
        response = given()
                .port(port)
                .pathParam("id", id)
                .when()
                .get(CarTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Then("CarResponse should contains car with ID {string}")
    public void carResponseShouldContainsCarWithId(String id) {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(CarResponse.class).getId())
                .isEqualTo(Long.parseLong(id));
    }

    @When("Find non existing car with ID {string}")
    public void carWithIdNotExists(String id) {
        response = given()
                .port(port)
                .pathParam("id", id)
                .when()
                .get(CarTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Then("Find should be thrown CarNotFoundException")
    public void carNotFoundExceptionThrownForId() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.as(ExceptionResponse.class).getMessage())
                .isEqualTo(CarTestUtil.CAR_NOT_FOUND_MESSAGE);
    }
}
