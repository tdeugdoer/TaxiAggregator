package com.tserashkevich.rideservice.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.tserashkevich.rideservice.dtos.*;
import com.tserashkevich.rideservice.integration.config.RideIntegrationTestConfig;
import com.tserashkevich.rideservice.utils.MappingToJsonUtil;
import com.tserashkevich.rideservice.utils.TestUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
public class RideIntegrationTest extends RideIntegrationTestConfig {
    @Autowired
    private MongoTemplate mongoTemplate;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection("rides");
        mongoTemplate.createCollection("rides");
        mongoTemplate.insert(TestUtil.getRide());
        mongoTemplate.insert(TestUtil.getSecondRide());
    }

    @Test
    void givenNonExistingRide_whenCreateRide_thenCreateNewRide() {
        CreateRideRequest request = TestUtil.getCreateRideRequest();
        CreateRideResponse response = TestUtil.getCreateRideResponse();

        passengerWireMock.stubFor(
                WireMock.get(TestUtil.EXIST_REQUEST + TestUtil.PASSENGER_ID)
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(Boolean.TRUE)))
        );
        geoapifyWireMock.stubFor(
                WireMock.get(urlPathMatching(TestUtil.GEO_POINT_REQUEST))
                        .withQueryParams(TestUtil.getGeoapifyStartGeoPointParams())
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(TestUtil.getStartGeocodeReverseResponse())))
        );
        geoapifyWireMock.stubFor(
                WireMock.get(urlPathMatching(TestUtil.GEO_POINT_REQUEST))
                        .withQueryParams(TestUtil.getGeoapifyEndGeoPointParams())
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(TestUtil.getEndGeocodeReverseResponse())))
        );
        geoapifyWireMock.stubFor(
                WireMock.get(urlPathMatching(TestUtil.DISTANCE_REQUEST))
                        .withQueryParams(TestUtil.getRoutingResponseParams())
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(TestUtil.getRoutingResponse())))
        );

        CreateRideResponse result = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestUtil.DEFAULT_PATH)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CreateRideResponse.class);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "startTime")
                .isEqualTo(response);
    }

    @Test
    void givenExistingRide_whenDeleteRide_thenDeleteRide() {
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .pathParam("id", TestUtil.RIDE_ID)
                .when()
                .delete(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void givenNonExistingRide_whenDeleteRide_thenReturnExceptionResponse() {
        ExceptionResponse result = given()
                .port(port)
                .pathParam("id", TestUtil.NON_EXISTING_RIDE_ID)
                .when()
                .delete(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(TestUtil.RIDE_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenValidParams_whenFindRides_thenReturnPageOfRideResponse() {
        Map<String, Object> findAllParamsMap = TestUtil.getFindAllParamsMap();
        PageResponse<RideResponse> expectedResponse = TestUtil.getPageResponse();

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
        List<RideResponse> Responses = result.jsonPath().getList("objectList", RideResponse.class);

        assertThat(Responses).hasSize(2);
        assertThat(Responses).usingRecursiveComparison()
                .ignoringFields("time.endTime")
                .isEqualTo(expectedResponse.getObjectList());
        assertThat(totalPage).isEqualTo(expectedResponse.getTotalPages());
        assertThat(totalElements).isEqualTo(expectedResponse.getTotalElements());
    }

    @Test
    void givenExistingRide_whenFindByIdRide_thenReturnRideResponse() {
        RideResponse response = TestUtil.getRideResponse();

        RideResponse result = given()
                .port(port)
                .pathParam("id", TestUtil.RIDE_ID)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RideResponse.class);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(response);
    }

    @Test
    void givenNonExistingRide_whenFindByIdRide_thenReturnExceptionResponse() {
        ExceptionResponse result = given()
                .port(port)
                .pathParam("id", TestUtil.NON_EXISTING_RIDE_ID)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(TestUtil.RIDE_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenExistingRide_whenChangeStatus_thenReturnRideResponse() {
        RideResponse response = TestUtil.getRideWithFinishedStatusResponse();

        RideResponse result = given()
                .port(port)
                .pathParam("rideId", TestUtil.RIDE_ID)
                .pathParam("status", TestUtil.STATUS_FINISHED)
                .when()
                .patch(TestUtil.DEFAULT_PATH + "/changeStatus/{rideId}" + "/{status}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RideResponse.class);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("time.endTime")
                .isEqualTo(response);
    }

    @Test
    void givenNonExistingRide_whenChangeStatus_thenReturnExceptionResponse() {
        ExceptionResponse result = given()
                .port(port)
                .pathParam("rideId", TestUtil.NON_EXISTING_RIDE_ID)
                .pathParam("status", TestUtil.STATUS_FINISHED)
                .when()
                .patch(TestUtil.DEFAULT_PATH + "/changeStatus/{rideId}" + "/{status}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(TestUtil.RIDE_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenExistingRide_whenChangeDriver_thenReturnRideResponse() {
        RideResponse response = TestUtil.getRideWithDriverResponse();

        driverWireMock.stubFor(
                WireMock.get(TestUtil.EXIST_REQUEST + TestUtil.DRIVER_ID)
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(Boolean.TRUE)))
        );

        RideResponse result = given()
                .port(port)
                .pathParam("id", TestUtil.RIDE_ID)
                .pathParam("driverId", TestUtil.DRIVER_ID)
                .when()
                .patch(TestUtil.DEFAULT_PATH + "/changeDriver/{id}" + "/{driverId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RideResponse.class);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(response);
    }

    @Test
    void givenNonExistingRide_whenChangeDriver_thenReturnExceptionResponse() {
        driverWireMock.stubFor(
                WireMock.get(TestUtil.EXIST_REQUEST + TestUtil.DRIVER_ID)
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(Boolean.TRUE)))
        );

        ExceptionResponse result = given()
                .port(port)
                .pathParam("rideId", TestUtil.NON_EXISTING_RIDE_ID)
                .pathParam("driverId", TestUtil.DRIVER_ID)
                .when()
                .patch(TestUtil.DEFAULT_PATH + "/changeDriver/{rideId}" + "/{driverId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(TestUtil.RIDE_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenExistingRide_whenChangeCar_thenReturnRideResponse() {
        RideResponse response = TestUtil.getRideWithCarResponse();

        carWireMock.stubFor(
                WireMock.get(TestUtil.EXIST_REQUEST + TestUtil.CAR_ID)
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(Boolean.TRUE)))
        );

        RideResponse result = given()
                .port(port)
                .pathParam("id", TestUtil.RIDE_ID)
                .pathParam("carId", TestUtil.CAR_ID)
                .when()
                .patch(TestUtil.DEFAULT_PATH + "/changeCar/{id}" + "/{carId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RideResponse.class);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(response);
    }

    @Test
    void givenNonExistingRide_whenChangeCar_thenReturnExceptionResponse() {
        carWireMock.stubFor(
                WireMock.get(TestUtil.EXIST_REQUEST + TestUtil.CAR_ID)
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", ContentType.JSON.toString())
                                .withBody(MappingToJsonUtil.toJson(Boolean.TRUE)))
        );

        ExceptionResponse result = given()
                .port(port)
                .pathParam("rideId", TestUtil.NON_EXISTING_RIDE_ID)
                .pathParam("carId", TestUtil.CAR_ID)
                .when()
                .patch(TestUtil.DEFAULT_PATH + "/changeCar/{rideId}" + "/{carId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(ExceptionResponse.class);

        assertThat(result.getMessage()).isEqualTo(TestUtil.RIDE_NOT_FOUND_MESSAGE);
    }

    @Test
    void givenExistingRide_whenCreateDriverComment_thenNothing() {
        CreateRatingRequest request = TestUtil.getSecondFinishedRideCreateRatingRequest();

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestUtil.DEFAULT_PATH + "/driverComment")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void givenNonExistingRide_whenCreateDriverComment_thenReturnExceptionResponse() {
        CreateRatingRequest request = TestUtil.getNonExistingRideCreateRatingRequest();

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestUtil.DEFAULT_PATH + "/driverComment")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void givenNotFinishedRide_whenCreateDriverComment_thenReturnExceptionResponse() {
        CreateRatingRequest request = TestUtil.getFinishedRideCreateRatingRequest();

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestUtil.DEFAULT_PATH + "/driverComment")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenExistingRide_whenCreatePassengerComment_thenNothing() {
        CreateRatingRequest request = TestUtil.getSecondFinishedRideCreateRatingRequest();

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestUtil.DEFAULT_PATH + "/passengerComment")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void givenNonExistingRide_whenCreatePassengerComment_thenReturnExceptionResponse() {
        CreateRatingRequest request = TestUtil.getNonExistingRideCreateRatingRequest();

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestUtil.DEFAULT_PATH + "/passengerComment")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void givenNotFinishedRide_whenCreatePassengerComment_thenReturnExceptionResponse() {
        CreateRatingRequest request = TestUtil.getFinishedRideCreateRatingRequest();

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(TestUtil.DEFAULT_PATH + "/passengerComment")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
