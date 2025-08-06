
package utils;
import static io.restassured.RestAssured.given;

/**
 * AuthenticationHelper
 * --------------------
 * Utility class for generating authentication tokens using POST /auth endpoint.
 */

public class AuthenticationHelper {
    public static String getToken() {
        return given().spec(ApiClient.getSpec())
            .body("{\"username\":\"admin\",\"password\":\"password123\"}")
        .when()
            .post("/auth")
        .then()
            .extract().path("token");
    }
}
