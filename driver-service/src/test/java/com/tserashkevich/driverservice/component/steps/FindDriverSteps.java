package com.tserashkevich.driverservice.component.steps;

import com.tserashkevich.driverservice.component.DriverAndCarComponentTest;
import com.tserashkevich.driverservice.dtos.DriverResponse;
import com.tserashkevich.driverservice.dtos.ExceptionResponse;
import com.tserashkevich.driverservice.utils.DriverTestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FindDriverSteps extends DriverAndCarComponentTest {
    @LocalServerPort
    private Integer port;
    private Response response;

    @When("Find existing driver with ID {string}")
    public void findByIdMethodIsCalledWithId(String id) {
        response = given()
                .port(port)
                .pathParam("id", id)
                .when()
                .get(DriverTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Then("DriverResponse should contains driver with ID {string}")
    public void driverResponseShouldContainsDriverWithId(String id) {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(DriverResponse.class).getId())
                .isEqualTo(UUID.fromString(id));
    }

    @When("Find non existing driver with ID {string}")
    public void driverWithIdNotExists(String id) {
        response = given()
                .port(port)
                .pathParam("id", id)
                .when()
                .get(DriverTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Then("Find should be thrown DriverNotFoundException")
    public void driverNotFoundExceptionThrownForId() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.as(ExceptionResponse.class).getMessage())
                .isEqualTo(DriverTestUtil.DRIVER_NOT_FOUND_MESSAGE);
    }
}
