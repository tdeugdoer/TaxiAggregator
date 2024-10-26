package com.tserashkevich.e2etests.steps;

import com.tserashkevich.e2etests.DataStepsClass;
import com.tserashkevich.e2etests.dtos.PassengerRequest;
import com.tserashkevich.e2etests.dtos.PassengerResponse;
import com.tserashkevich.e2etests.utils.TestUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PassengerSteps extends DataStepsClass {
    private PassengerRequest passengerRequest;

    @Given("PassengerRequest with name {string} and phone {string}")
    public void passengerRequestWithNameAndPhone(String name, String phone) {
        passengerRequest = TestUtil.getPassengerRequest(name, phone);
    }

    @When("Send create request to passenger-service with passengerRequest")
    public void sendCreateRequestToPassengerServiceWithPassengerRequest() {
        passengerResponse = given()
                .baseUri(TestUtil.BASE_URL + TestUtil.PASSENGERS_PORT)
                .contentType(ContentType.JSON)
                .body(passengerRequest)
                .when()
                .post(TestUtil.PASSENGERS_PATH)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(PassengerResponse.class);
    }

    @Then("PassengerResponse should not be null")
    public void passengerResponseShouldNotBeNull() {
        assertThat(passengerResponse).isNotNull();
    }

    @And("PassengerResponse should contains name {string}")
    public void passengerResponseShouldContainsName(String name) {
        assertThat(passengerResponse.getName()).isEqualTo(name);
    }

    @And("PassengerResponse should contains phone {string}")
    public void passengerResponseShouldContainsPhone(String phone) {
        assertThat(passengerResponse.getPhoneNumber()).isEqualTo(phone);
    }
}
