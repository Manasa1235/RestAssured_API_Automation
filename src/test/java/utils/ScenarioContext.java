package utils;

import io.restassured.response.Response;

/**
 * ScenarioContext
 * ---------------
 * Global context holder for sharing state across step definition classes.
 * Stores token, bookingId, and response so they can be reused between steps.
 */

public class ScenarioContext {
    public static String token;
    public static int bookingId;
    public static Response response;
}
