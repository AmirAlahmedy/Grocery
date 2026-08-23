package com.amir.bdd;

import com.amir.inventory.InventoryApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.context.ActiveProfiles;


@CucumberContextConfiguration
@SpringBootTest(
        classes = InventoryApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class CucumberSpringConfiguration {
    // Spring context configuration shared across step definitions
}