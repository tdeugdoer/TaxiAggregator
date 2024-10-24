package com.tserashkevich.ratingservice.component.steps;

import com.tserashkevich.ratingservice.component.RatingComponentTest;
import com.tserashkevich.ratingservice.dtos.ExceptionResponse;
import com.tserashkevich.ratingservice.utils.TestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteRatingSteps extends RatingComponentTest {
    @LocalServerPort
    private Integer port;
    private Response response;

    @When("Delete rating method is called with ID {string}")
    public void deleteByIdMethodIsCalledWithId(String id) {
        response = given()
                .port(port)
                .pathParam("id", id)
                .when()
                .delete(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    @Then("Should return No Content")
    public void ratingResponseShouldContainsRatingWithId() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Then("Delete should be thrown RatingNotFoundException")
    public void ratingNotFoundExceptionThrownForId() {
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.as(ExceptionResponse.class).getMessage())
                .isEqualTo(TestUtil.RATING_NOT_FOUND_MESSAGE);
    }
}
