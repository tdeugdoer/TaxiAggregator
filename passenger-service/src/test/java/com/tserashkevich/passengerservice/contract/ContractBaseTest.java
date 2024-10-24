package com.tserashkevich.passengerservice.contract;

import com.tserashkevich.passengerservice.contract.config.ContractTestConfig;
import com.tserashkevich.passengerservice.controllers.PassengerController;
import com.tserashkevich.passengerservice.dtos.PassengerResponse;
import com.tserashkevich.passengerservice.utils.TestUtil;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


public class ContractBaseTest extends ContractTestConfig {
    @LocalServerPort
    private Integer port;

    @Autowired
    private PassengerController passengerController;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(passengerController);
    }

    @Test
    void givenExistingPassenger_whenFindByIdPassenger_thenReturnPassengerResponse() {
        PassengerResponse response = TestUtil.getSecondPassengerResponse();

        PassengerResponse result = given()
                .port(port)
                .pathParam("id", TestUtil.SECOND_ID)
                .when()
                .get(TestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PassengerResponse.class);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(response);
    }
}
