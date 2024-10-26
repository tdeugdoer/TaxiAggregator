package com.tserashkevich.passengerservice.component;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql(
        scripts = {
                "classpath:sql/delete-data.sql",
                "classpath:sql/insert-data.sql"
        }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features")
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PassengerComponentTest {
}