package com.tserashkevich.e2etests.steps;

import com.tserashkevich.e2etests.DataStepsClass;
import com.tserashkevich.e2etests.dtos.DriverRequest;
import com.tserashkevich.e2etests.dtos.DriverResponse;
import com.tserashkevich.e2etests.utils.TestUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DriverSteps extends DataStepsClass {
    private DriverRequest driverRequest;

    @Given("DriverRequest with name {string}, phone {string} and car number {string}")
    public void driverRequestWithNamePhoneAndCarNumber(String name, String phone, String carNumber) {
        driverRequest = TestUtil.getDriverRequest(name, phone, carNumber);
    }

    @When("Send create request to driver-service with driverRequest")
    public void sendCreateRequestToDriverServiceWithDriverRequest() {
        driverResponse = given()
                .baseUri(TestUtil.BASE_URL + TestUtil.DRIVERS_PORT)
                .basePath(TestUtil.DRIVERS_PATH)
                .contentType(ContentType.JSON)
                .body(driverRequest)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(DriverResponse.class);
    }

    @Then("DriverResponse should not be null")
    public void driverResponseShouldNotBeNull() {
        assertThat(driverResponse).isNotNull();
    }

    @And("DriverResponse should contains name {string}")
    public void driverResponseShouldContainsName(String name) {
        assertThat(driverResponse.getName()).isEqualTo(name);
    }

    @And("DriverResponse should contains phone {string}")
    public void driverResponseShouldContainsPhone(String phone) {
        assertThat(driverResponse.getPhoneNumber()).isEqualTo(phone);
    }

    @And("DriverResponse should contains car number {string}")
    public void driverResponseShouldContainsCarNumber(String carNumber) {
        assertThat(driverResponse.getCars().get(0).getNumber()).isEqualTo(carNumber);
    }
}
