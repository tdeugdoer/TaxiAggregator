package com.tserashkevich.driverservice.component.steps;

import com.tserashkevich.driverservice.component.DriverAndCarComponentTest;
import com.tserashkevich.driverservice.dtos.CarRequest;
import com.tserashkevich.driverservice.dtos.CarResponse;
import com.tserashkevich.driverservice.dtos.ValidationErrorResponse;
import com.tserashkevich.driverservice.utils.CarTestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UpdateCarSteps extends DriverAndCarComponentTest {
    @LocalServerPort
    private Integer port;
    private Response response;

    @When("Update car method is called with ID {string} and CarRequest of number {string} and driver {string}")
    public void updateCarMethodIsCalledWithCarRequestOfNumberAndDriver(String id, String number, String driver) {
        CarRequest request = CarTestUtil.getCarRequest(number, driver);

        response = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(request)
                .when()
                .put(CarTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Then("CarResponse should contains car with ID {string} and number {string} and driver {string}")
    public void carResponseShouldContainsCarWithIdAndNumberAndDriver(String id, String number, String driver) {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());

        CarResponse carResponse = response.as(CarResponse.class);

        assertThat(carResponse.getId())
                .isEqualTo(Long.parseLong(id));
        assertThat(carResponse.getNumber())
                .isEqualTo(number);
        assertThat(carResponse.getDriver())
                .isEqualTo(UUID.fromString(driver));
    }

    @Then("Update ValidationErrorResponse should contains number and driver")
    public void ValidationErrorResponseShouldBeContainsAndPhone() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());

        ValidationErrorResponse validationErrorResponse = response.as(ValidationErrorResponse.class);

        assertThat(validationErrorResponse.getViolations().get(0).getFieldName())
                .isIn("number", "driver");
        assertThat(validationErrorResponse.getViolations().get(1).getFieldName())
                .isIn("number", "driver");
    }
}
