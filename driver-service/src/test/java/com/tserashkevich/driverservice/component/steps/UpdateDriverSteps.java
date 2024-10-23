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

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UpdateDriverSteps extends DriverAndCarComponentTest {
    @LocalServerPort
    private Integer port;
    private Response response;

    @When("Update driver method is called with ID {string} and DriverRequest of username {string} and phone {string}")
    public void updateDriverMethodIsCalledWithDriverRequestOfUsernameAndPhone(String id, String username, String phone) {
        DriverRequest request = DriverTestUtil.getDriverRequest(username, phone);

        response = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(request)
                .when()
                .put(DriverTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Then("DriverResponse should contains driver with ID {string} and username {string} and phone {string}")
    public void driverResponseShouldContainsDriverWithIdAndUsernameAndPhone(String id, String username, String phone) {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());

        DriverResponse driverResponse = response.as(DriverResponse.class);

        assertThat(driverResponse.getId())
                .isEqualTo(UUID.fromString(id));
        assertThat(driverResponse.getName())
                .isEqualTo(username);
        assertThat(driverResponse.getPhoneNumber())
                .isEqualTo(phone);
    }

    @Then("Update ValidationErrorResponse should contains phone")
    public void ValidationErrorResponseShouldBeContainsAndPhone() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());

        ValidationErrorResponse validationErrorResponse = response.as(ValidationErrorResponse.class);

        assertThat(validationErrorResponse.getViolations().get(0).getFieldName())
                .isEqualTo("phoneNumber");
    }
}
