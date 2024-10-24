package com.tserashkevich.driverservice.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.tserashkevich.driverservice.dtos.*;
import com.tserashkevich.driverservice.integration.config.DriverIntegrationTestConfig;
import com.tserashkevich.driverservice.utils.DriverTestUtil;
import com.tserashkevich.driverservice.utils.MappingToJsonUtil;
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

public class DriverIntegrationTest extends DriverIntegrationTestConfig {
    @LocalServerPort
    private Integer port;

    @Test
    void givenNonExistingDriverWitchCars_whenCreateDriver_thenCreateNewDriver() {
        DriverRequest request = DriverTestUtil.getNonExistingDriverRequest();
        DriverResponse response = DriverTestUtil.getCreatedCarResponse();

        wireMockExtension.stubFor(
                WireMock.get(urlPathMatching(DriverTestUtil.AVG_REQUEST_WITH_UUID))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(DriverTestUtil.RATING)))
        );

        DriverResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(DriverTestUtil.DEFAULT_PATH)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(DriverResponse.class);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(response);
    }

    @Test
    void givenNonExistingDriverWithoutCars_whenCreateDriver_thenCreateNewDriver() {
        DriverRequest request = DriverTestUtil.getInvalidPassengerRequest();

        ValidationErrorResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(DriverTestUtil.DEFAULT_PATH)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .as(ValidationErrorResponse.class);

        assertThat(result.getViolations()).hasSize(4);
    }

    @Test
    void givenExistingDriver_whenEditDriver_thenUpdateDriver() {
        DriverRequest request = DriverTestUtil.getNonExistingDriverRequest();
        DriverResponse response = DriverTestUtil.getCreatedCarResponse();

        wireMockExtension.stubFor(
                WireMock.get(urlPathMatching(DriverTestUtil.AVG_REQUEST_WITH_UUID))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(DriverTestUtil.RATING)))
        );

        DriverResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", DriverTestUtil.ID)
                .body(request)
                .when()
                .put(DriverTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(DriverResponse.class);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "cars")
                .isEqualTo(response);
    }

    @Test
    void givenNonExistingDriver_whenEditDriver_thenThrowException() {
        DriverRequest request = DriverTestUtil.getNonExistingDriverRequest();

        ExceptionResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", DriverTestUtil.NON_EXISTING_ID)
                .body(request)
                .when()
                .put(DriverTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(DriverTestUtil.DRIVER_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenExistingDriver_whenDeleteDriver_thenDeleteDriver() {
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", DriverTestUtil.ID)
                .when()
                .delete(DriverTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void givenNonExistingDriver_whenDeleteDriver_thenThrowException() {
        ExceptionResponse result = given()
                .port(port)
                .pathParam("id", DriverTestUtil.NON_EXISTING_ID)
                .when()
                .delete(DriverTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(DriverTestUtil.DRIVER_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenValidParams_whenFindDrivers_thenReturnPageOfDriverResponse() {
        Map<String, Object> findAllParamsMap = DriverTestUtil.getFindAllParamsMap();
        PageResponse<DriverResponse> expectedResponse = DriverTestUtil.getPageResponse();

        wireMockExtension.stubFor(
                WireMock.get(DriverTestUtil.AVG_REQUEST + DriverTestUtil.ID)
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(DriverTestUtil.RATING)))
        );

        wireMockExtension.stubFor(
                WireMock.get(DriverTestUtil.AVG_REQUEST + DriverTestUtil.SECOND_ID)
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(DriverTestUtil.SECOND_RATING)))
        );

        Response result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .params(findAllParamsMap)
                .when()
                .get(DriverTestUtil.DEFAULT_PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        Integer totalPage = result.jsonPath().getInt("totalPages");
        Long totalElements = result.jsonPath().getLong("totalElements");
        List<DriverResponse> driverResponses = result.jsonPath().getList("objectList", DriverResponse.class);

        assertThat(driverResponses).hasSize(2);
        assertThat(driverResponses).usingRecursiveComparison().isEqualTo(expectedResponse.getObjectList());
        assertThat(totalPage).isEqualTo(expectedResponse.getTotalPages());
        assertThat(totalElements).isEqualTo(expectedResponse.getTotalElements());
    }

    @Test
    void givenExistingDriver_whenFindByIdDriver_thenReturnDriverResponse() {
        DriverResponse response = DriverTestUtil.getDriverResponse();

        wireMockExtension.stubFor(
                WireMock.get(urlPathMatching(DriverTestUtil.AVG_REQUEST_WITH_UUID))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(DriverTestUtil.RATING)))
        );

        DriverResponse result = given()
                .port(port)
                .pathParam("id", DriverTestUtil.ID)
                .when()
                .get(DriverTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(DriverResponse.class);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(response);
    }

    @Test
    void givenNonExistingDriver_whenFindByIdDriver_thenThrowException() {
        ExceptionResponse result = given()
                .port(port)
                .pathParam("id", DriverTestUtil.NON_EXISTING_ID)
                .when()
                .get(DriverTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(DriverTestUtil.DRIVER_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenExistingDriver_whenChangeAvailableStatus_thenReturnDriverResponse() {
        DriverResponse response = DriverTestUtil.getDriverResponseWithFalseAvailable();

        wireMockExtension.stubFor(
                WireMock.get(urlPathMatching(DriverTestUtil.AVG_REQUEST_WITH_UUID))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(DriverTestUtil.RATING)))
        );

        DriverResponse result = given()
                .port(port)
                .pathParam("id", DriverTestUtil.ID)
                .pathParam("available", DriverTestUtil.NON_AVAILABLE)
                .when()
                .patch(DriverTestUtil.DEFAULT_PATH + "/changeStatus/{id}" + "/{available}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(DriverResponse.class);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(response);
    }

    @Test
    void givenNonExistingDriver_whenChangeAvailableStatus_thenThrowException() {
        ExceptionResponse result = given()
                .port(port)
                .pathParam("id", DriverTestUtil.NON_EXISTING_ID)
                .pathParam("available", DriverTestUtil.NON_AVAILABLE)
                .when()
                .patch(DriverTestUtil.DEFAULT_PATH + "/changeStatus/{id}" + "/{available}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(DriverTestUtil.DRIVER_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenExistingDriver_whenExistByIdDriver_thenTrue() {
        Boolean result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", DriverTestUtil.ID)
                .when()
                .get(DriverTestUtil.DEFAULT_PATH + "/exist/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Boolean.class);

        assertThat(result).isEqualTo(true);
    }

    @Test
    void givenNonExistingDriver_whenExistByIdDriver_thenFalse() {
        Boolean result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", DriverTestUtil.NON_EXISTING_ID)
                .when()
                .get(DriverTestUtil.DEFAULT_PATH + "/exist/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Boolean.class);

        assertThat(result).isEqualTo(false);
    }
}
