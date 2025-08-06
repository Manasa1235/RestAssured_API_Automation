
package utils;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import static io.restassured.http.ContentType.JSON;

/**
 * ApiClient
 * ---------
 * Provides a reusable RestAssured RequestSpecification for all API calls.
 * Centralizes base URI, headers, and content type configuration.
 */

public class ApiClient {
    public static RequestSpecification getSpec() {
        return new RequestSpecBuilder()
            .setBaseUri("https://restful-booker.herokuapp.com")
            .setContentType(JSON)
            .build();
    }
}
