package com.tserashkevich.passengerservice.component.steps;

import com.tserashkevich.passengerservice.component.PassengerComponentTest;
import com.tserashkevich.passengerservice.dtos.PassengerRequest;
import com.tserashkevich.passengerservice.dtos.PassengerResponse;
import com.tserashkevich.passengerservice.dtos.ValidationErrorResponse;
import com.tserashkevich.passengerservice.utils.TestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreatePassengerSteps extends PassengerComponentTest {
    @LocalServerPort
    private Integer port;
    private Response response;

    @When("Create passenger with username {string} and phone {string}")
    public void createPassengerMethodIsCalledWithPassengerRequestOfUsernameAndPhone(String username, String phone) {
        PassengerRequest request = TestUtil.getPassengerRequest(username, phone);

        response = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestUtil.DEFAULT_PATH)
                .then()
                .extract()
                .response();
    }

    @Then("Create PassengerResponse should contains passenger with username {string} and phone {string}")
    public void passengerResponseShouldContainsPassengerWithUsernameAndPhone(String username, String phone) {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.CREATED.value());

        PassengerResponse passengerResponse = response.as(PassengerResponse.class);

        assertThat(passengerResponse.getName())
                .isEqualTo(username);
        assertThat(passengerResponse.getPhoneNumber())
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
