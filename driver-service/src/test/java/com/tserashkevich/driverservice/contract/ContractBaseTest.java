package com.tserashkevich.driverservice.contract;

import com.tserashkevich.driverservice.contract.config.ContractTestConfig;
import com.tserashkevich.driverservice.controllers.CarController;
import com.tserashkevich.driverservice.controllers.DriverController;
import com.tserashkevich.driverservice.dtos.DriverResponse;
import com.tserashkevich.driverservice.utils.DriverTestUtil;
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
    private DriverController driverController;

    @Autowired
    private CarController carController;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(driverController, carController);
    }

    @Test
    void givenExistingDriver_whenFindByIdDriver_thenReturnDriverResponse() {
        DriverResponse response = DriverTestUtil.getSecondDriverResponse();

        DriverResponse result = given()
                .port(port)
                .pathParam("id", DriverTestUtil.SECOND_ID)
                .when()
                .get(DriverTestUtil.DEFAULT_PATH + "/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(DriverResponse.class);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("cars")
                .isEqualTo(response);
    }
}
