package com.tserashkevich.passengerservice.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.tserashkevich.passengerservice.dtos.*;
import com.tserashkevich.passengerservice.integration.config.PassengerIntegrationTestConfig;
import com.tserashkevich.passengerservice.utils.MappingToJsonUtil;
import com.tserashkevich.passengerservice.utils.TestUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PassengerIntegrationTest extends PassengerIntegrationTestConfig {
    @LocalServerPort
    private Integer port;

    @Test
    void givenValidPassengerRequest_whenCreatePassenger_thenReturnPassengerResponse() {
        PassengerRequest request = TestUtil.getNonExistingPassengerRequest();
        PassengerResponse response = TestUtil.getCreatedPassengerResponse();

        wireMockExtension.stubFor(
                WireMock.get(urlPathMatching(TestUtil.AVG_REQUEST_WITH_UUID))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(TestUtil.RATING)))
        );

        PassengerResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestUtil.DEFAULT_PATH)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(PassengerResponse.class);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(response);
    }

    @Test
    void givenInvalidPassengerRequest_whenCreatePassenger_thenReturnExceptionResponse() {
        PassengerRequest request = TestUtil.getInvalidPassengerRequest();

        ValidationErrorResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestUtil.DEFAULT_PATH)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .as(ValidationErrorResponse.class);

        assertThat(result.getViolations()).hasSize(4);
    }

    @Test
    void givenExistingPassenger_whenEditPassenger_thenUpdatePassenger() {
        PassengerRequest request = TestUtil.getNonExistingPassengerRequest();
        PassengerResponse response = TestUtil.getCreatedPassengerResponse();

        wireMockExtension.stubFor(
                WireMock.get(urlPathMatching(TestUtil.AVG_REQUEST_WITH_UUID))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(TestUtil.RATING)))
        );

        PassengerResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", TestUtil.ID)
                .body(request)
                .when()
                .put(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PassengerResponse.class);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(response);
    }

    @Test
    void givenNonExistingPassenger_whenEditPassenger_thenReturnExceptionResponse() {
        PassengerRequest request = TestUtil.getNonExistingPassengerRequest();

        ExceptionResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", TestUtil.NON_EXISTING_ID)
                .body(request)
                .when()
                .put(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(TestUtil.PASSENGER_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenExistingPassenger_whenDeletePassenger_thenDeletePassenger() {
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", TestUtil.ID)
                .when()
                .delete(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void givenNonExistingPassenger_whenDeletePassenger_thenReturnExceptionResponse() {
        ExceptionResponse result = given()
                .port(port)
                .pathParam("id", TestUtil.NON_EXISTING_ID)
                .when()
                .delete(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(TestUtil.PASSENGER_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenValidParams_whenFindPassengers_thenReturnPageOfPassengerResponse() {
        Map<String, Object> findAllParamsMap = TestUtil.getFindAllParamsMap();
        PageResponse<PassengerResponse> expectedResponse = TestUtil.getPageResponse();

        wireMockExtension.stubFor(
                WireMock.get(TestUtil.AVG_REQUEST + TestUtil.ID)
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(TestUtil.RATING)))
        );

        wireMockExtension.stubFor(
                WireMock.get(TestUtil.AVG_REQUEST + TestUtil.SECOND_ID)
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(TestUtil.SECOND_RATING)))
        );

        Response result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .params(findAllParamsMap)
                .when()
                .get(TestUtil.DEFAULT_PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        Integer totalPage = result.jsonPath().getInt("totalPages");
        Long totalElements = result.jsonPath().getLong("totalElements");
        List<PassengerResponse> passengerResponses = result.jsonPath().getList("objectList", PassengerResponse.class);

        assertThat(passengerResponses).hasSize(2);
        assertThat(passengerResponses).usingRecursiveComparison().isEqualTo(expectedResponse.getObjectList());
        assertThat(totalPage).isEqualTo(expectedResponse.getTotalPages());
        assertThat(totalElements).isEqualTo(expectedResponse.getTotalElements());
    }

    @Test
    void givenExistingPassenger_whenFindByIdPassenger_thenReturnPassengerResponse() {
        PassengerResponse response = TestUtil.getPassengerResponse();

        wireMockExtension.stubFor(
                WireMock.get(urlPathMatching(TestUtil.AVG_REQUEST_WITH_UUID))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(TestUtil.RATING)))
        );

        PassengerResponse result = given()
                .port(port)
                .pathParam("id", TestUtil.ID)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PassengerResponse.class);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(response);
    }

    @Test
    void givenNonExistingPassenger_whenFindByIdPassenger_thenThrowException() {
        ExceptionResponse result = given()
                .port(port)
                .pathParam("id", TestUtil.NON_EXISTING_ID)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(TestUtil.PASSENGER_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenExistingPassenger_whenExistByIdPassenger_thenReturnTrue() {
        Boolean result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", TestUtil.ID)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/exist/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Boolean.class);

        assertThat(result).isEqualTo(true);

    }

    @Test
    void givenNonExistingPassenger_whenExistByIdPassenger_thenReturnFalse() {
        Boolean result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", TestUtil.NON_EXISTING_ID)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/exist/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Boolean.class);

        assertThat(result).isEqualTo(false);
    }
}
