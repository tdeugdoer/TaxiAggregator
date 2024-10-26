package com.tserashkevich.ratingservice.contract;

import com.tserashkevich.ratingservice.controllers.RatingController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@AutoConfigureMessageVerifier
public class ContractBaseTest {
    @Autowired
    private RatingController ratingController;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(ratingController);
    }
}
