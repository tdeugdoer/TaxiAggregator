package com.tserashkevich.ratingservice.component.steps;

import com.tserashkevich.ratingservice.component.RatingComponentTest;
import com.tserashkevich.ratingservice.dtos.ExceptionResponse;
import com.tserashkevich.ratingservice.dtos.RatingResponse;
import com.tserashkevich.ratingservice.utils.TestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FindRatingSteps extends RatingComponentTest {
    @LocalServerPort
    private Integer port;
    private Response response;

    @When("Find existing rating with ID {string}")
    public void findByIdMethodIsCalledWithId(String id) {
        response = given()
                .port(port)
                .pathParam("id", id)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Then("RatingResponse should contains rating with ID {string}")
    public void ratingResponseShouldContainsRatingWithId(String id) {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(RatingResponse.class).getId())
                .isEqualTo(UUID.fromString(id));
    }

    @When("Find non existing rating with ID {string}")
    public void ratingWithIdNotExists(String id) {
        response = given()
                .port(port)
                .pathParam("id", id)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Then("Find should be thrown RatingNotFoundException")
    public void ratingNotFoundExceptionThrownForId() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.as(ExceptionResponse.class).getMessage())
                .isEqualTo(TestUtil.RATING_NOT_FOUND_MESSAGE);
    }
}
