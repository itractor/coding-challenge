package io.bankbridge;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankRemoteCallsTest extends CoreFunctionalityTest{


    /**
     * Ensure that te endpoint exist
     */
    @Test
    public void existentEndpointTest() {
        given().when().get("/v2/banks/all").then().statusCode(200);
    }

    /**
     * Test that name exists
     */
    @Test
    public void verifyName() {
        assertTrue(
                given().when().get("/v2/banks/all").then()
                        .extract().jsonPath().getString("[0].name").equals("Credit Sweets")
        );
    }

    /**
     * Test that name exists
     */
    @Test
    public void verifyId() {
        assertTrue(
                given().when().get("/v2/banks/all").then()
                        .extract().jsonPath().getString("[0].id").equals("5678")
        );
    }


}
