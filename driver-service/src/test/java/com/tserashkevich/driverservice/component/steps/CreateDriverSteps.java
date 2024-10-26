package com.tserashkevich.driverservice.component.steps;

import com.tserashkevich.driverservice.component.DriverAndCarComponentTest;
import com.tserashkevich.driverservice.dtos.DriverRequest;
import com.tserashkevich.driverservice.dtos.DriverResponse;
import com.tserashkevich.driverservice.dtos.ValidationErrorResponse;
import com.tserashkevich.driverservice.utils.DriverTestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreateDriverSteps extends DriverAndCarComponentTest {
    @LocalServerPort
    private Integer port;
    private Response response;

    @When("Create driver with username {string} and phone {string}")
    public void createDriverMethodIsCalledWithDriverRequestOfUsernameAndPhone(String username, String phone) {
        DriverRequest request = DriverTestUtil.getDriverRequest(username, phone);

        response = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(DriverTestUtil.DEFAULT_PATH)
                .then()
                .extract()
                .response();
    }

    @Then("Create DriverResponse should contains driver with username {string} and phone {string}")
    public void driverResponseShouldContainsDriverWithUsernameAndPhone(String username, String phone) {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.CREATED.value());

        DriverResponse driverResponse = response.as(DriverResponse.class);

        assertThat(driverResponse.getName())
                .isEqualTo(username);
        assertThat(driverResponse.getPhoneNumber())
                .isEqualTo(phone);
    }

    @Then("ValidationErrorResponse should contains phone")
    public void ValidationErrorResponseShouldBeContainsAndPhone() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());

        ValidationErrorResponse validationErrorResponse = response.as(ValidationErrorResponse.class);

        assertThat(validationErrorResponse.getViolations().get(0).getFieldName())
                .isEqualTo("phoneNumber");
    }
}
