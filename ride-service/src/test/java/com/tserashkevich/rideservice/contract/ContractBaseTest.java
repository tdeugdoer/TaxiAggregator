package com.tserashkevich.rideservice.contract;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.tserashkevich.rideservice.contract.config.ContractTestConfig;
import com.tserashkevich.rideservice.dtos.CreateRideResponse;
import com.tserashkevich.rideservice.dtos.RideResponse;
import com.tserashkevich.rideservice.utils.MappingToJsonUtil;
import com.tserashkevich.rideservice.utils.TestUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


public class ContractBaseTest extends ContractTestConfig {
    @LocalServerPort
    private Integer port;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection("rides");
        mongoTemplate.createCollection("rides");
        mongoTemplate.insert(TestUtil.getRide());
        mongoTemplate.insert(TestUtil.getSecondRide());
    }

    @Test
    public void givenCreateRideRequest_WhenPassengerExist_ThenReturnTrue() {
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
                .body(TestUtil.getSecondCreateRideRequest())
                .when()
                .post(TestUtil.DEFAULT_PATH)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CreateRideResponse.class);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "startTime")
                .isEqualTo(TestUtil.getSecondCreateRideResponse());
    }

    @Test
    public void givenCreateRideRequest_WhenPassengerNotExist_ThenReturnFalse() {
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(TestUtil.getCreateRideRequest())
                .when()
                .post(TestUtil.DEFAULT_PATH)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenExistingRide_whenChangeDriver_thenReturnRideResponse() {
        RideResponse response = TestUtil.getRideWithDriverResponse();

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
        given()
                .port(port)
                .pathParam("rideId", TestUtil.RIDE_ID)
                .pathParam("driverId", TestUtil.NON_EXISTING_DRIVER_ID)
                .when()
                .patch(TestUtil.DEFAULT_PATH + "/changeDriver/{rideId}" + "/{driverId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenExistingRide_whenChangeCar_thenReturnRideResponse() {
        RideResponse response = TestUtil.getSecondRideWithCarResponse();

        RideResponse result = given()
                .port(port)
                .pathParam("id", TestUtil.RIDE_ID)
                .pathParam("carId", TestUtil.SECOND_CAR_ID)
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
        given()
                .port(port)
                .pathParam("rideId", TestUtil.RIDE_ID)
                .pathParam("carId", TestUtil.NON_EXISTING_CAR_ID)
                .when()
                .patch(TestUtil.DEFAULT_PATH + "/changeCar/{rideId}" + "/{carId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
