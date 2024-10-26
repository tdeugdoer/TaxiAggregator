package com.tserashkevich.driverservice.integration;

import com.tserashkevich.driverservice.dtos.CarRequest;
import com.tserashkevich.driverservice.dtos.CarResponse;
import com.tserashkevich.driverservice.dtos.ExceptionResponse;
import com.tserashkevich.driverservice.dtos.PageResponse;
import com.tserashkevich.driverservice.integration.config.CarIntegrationTestConfig;
import com.tserashkevich.driverservice.utils.CarTestUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CarIntegrationTest extends CarIntegrationTestConfig {
    @LocalServerPort
    private Integer port;

    @Test
    void givenNonExistingCar_whenCreateCar_thenReturnCarResponse() {
        CarRequest request = CarTestUtil.getNonExistingCarRequest();
        CarResponse response = CarTestUtil.getCreatedCarResponse();

        CarResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(CarTestUtil.DEFAULT_PATH)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CarResponse.class);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(response);
    }

    @Test
    void givenExistingCar_whenEditCar_thenReturnCarResponse() {
        CarRequest request = CarTestUtil.getNonExistingCarRequest();
        CarResponse response = CarTestUtil.getCreatedCarResponse();

        CarResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", CarTestUtil.ID)
                .body(request)
                .when()
                .put(CarTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CarResponse.class);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(response);
    }

    @Test
    void givenNonExistingCar_whenEditCar_thenReturnExceptionResponse() {
        CarRequest request = CarTestUtil.getNonExistingCarRequest();

        ExceptionResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", CarTestUtil.NON_EXISTING_ID)
                .body(request)
                .when()
                .put(CarTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(CarTestUtil.CAR_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenExistingCar_whenDeleteCar_thenDeleteCar() {
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", CarTestUtil.ID)
                .when()
                .delete(CarTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void givenNonExistingCar_whenDeleteCar_thenReturnExceptionResponse() {
        ExceptionResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", CarTestUtil.NON_EXISTING_ID)
                .when()
                .delete(CarTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(CarTestUtil.CAR_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenValidParams_whenFindCars_thenReturnPageOfCarResponse() {
        Map<String, Object> findAllParamsMap = CarTestUtil.getFindAllParamsMap();
        PageResponse<CarResponse> expectedResponse = CarTestUtil.getPageResponse();

        Response result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .params(findAllParamsMap)
                .when()
                .get(CarTestUtil.DEFAULT_PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        Integer totalPage = result.jsonPath().getInt("totalPages");
        Long totalElements = result.jsonPath().getLong("totalElements");
        List<CarResponse> passengerResponses = result.jsonPath().getList("objectList", CarResponse.class);

        assertThat(passengerResponses).hasSize(2);
        assertThat(passengerResponses).usingRecursiveComparison().isEqualTo(expectedResponse.getObjectList());
        assertThat(totalPage).isEqualTo(expectedResponse.getTotalPages());
        assertThat(totalElements).isEqualTo(expectedResponse.getTotalElements());
    }

    @Test
    void givenExistingCar_whenFindByIdCar_thenReturnCarResponse() {
        CarResponse response = CarTestUtil.getCarResponse();

        CarResponse result = given()
                .port(port)
                .pathParam("id", CarTestUtil.ID)
                .when()
                .get(CarTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CarResponse.class);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(response);
    }

    @Test
    void givenNonExistingCar_whenFindByIdCar_thenExceptionResponse() {
        ExceptionResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", CarTestUtil.NON_EXISTING_ID)
                .when()
                .get(CarTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(CarTestUtil.CAR_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenExistingCar_whenExistByIdCar_thenTrue() {
        Boolean result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", CarTestUtil.ID)
                .when()
                .get(CarTestUtil.DEFAULT_PATH + "/exist/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Boolean.class);

        assertThat(result).isEqualTo(true);
    }

    @Test
    void givenNonExistingCar_whenExistByIdCar_thenFalse() {
        Boolean result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", CarTestUtil.NON_EXISTING_ID)
                .when()
                .get(CarTestUtil.DEFAULT_PATH + "/exist/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Boolean.class);

        assertThat(result).isEqualTo(false);
    }
}
