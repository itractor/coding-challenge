package io.bankbridge;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankCacheBasedTest extends CoreFunctionalityTest{

    /**
     * Ensure that te endpoint exist
     */
    @Test
    public void existentEndpointTest() {
        given().when().get("/v1/banks/all").then().statusCode(200);
    }

    /**
     * Test that name exists
     */
    @Test
    public void verifyName() {
        assertTrue(
                given().when().get("/v1/banks/all").then()
                        .extract().jsonPath().getString("[0].name").equals("Credit Sweets")
        );
    }

    /**
     * Test that id exists
     */
    @Test
    public void verifyId() {
        assertTrue(
                given().when().get("/v1/banks/all").then()
                        .extract().jsonPath().getString("[0].id").equals("5678")
        );
    }

}
