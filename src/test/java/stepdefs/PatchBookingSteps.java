package stepdefs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.response.Response;
import utils.ApiClient;
import utils.JsonDataReader;
import utils.ScenarioContext;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.assertEquals;

public class PatchBookingSteps {



    @Given("User creates a new booking with data {string}")
    public void userCreatesANewBookingWithData(String dataKey) {
        ScenarioContext.response = given().spec(ApiClient.getSpec())
                .body(JsonDataReader.getData(dataKey))
                .when()
                .post("/booking");

        ScenarioContext.response.then().statusCode(200);

        ScenarioContext.bookingId =  ScenarioContext.response.jsonPath().getInt("bookingid");
        System.out.println("Booking created with ID: " + ScenarioContext.bookingId);
    }

    @When("User partially updates booking with data:")
    public void user_partially_updates_booking_map(DataTable table) {
        Map<String, Object> data = new HashMap<>();

        table.asMap().forEach((key, value) -> {
            if (key.equals("bookingdates")) {
                // Parse JSON string into a map
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, String> bookingDates = mapper.readValue(value, new TypeReference<Map<String, String>>() {});
                    data.put("bookingdates", bookingDates);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to parse bookingdates JSON", e);
                }
            } else {
                data.put(key, value);
            }
        });

        ScenarioContext.response = given().spec(ApiClient.getSpec())
                .cookie("token", ScenarioContext.token)
                .body(data)
                .when()
                .patch("/booking/" + ScenarioContext.bookingId);
    }

    @When("User partially updates booking with data {string}")
    public void user_partially_updates_booking_json(String dataKey) {
        ScenarioContext.response = given().spec(ApiClient.getSpec())
                .cookie("token", ScenarioContext.token)
                .body(JsonDataReader.getData(dataKey))
                .when()
                .patch("/booking/" + ScenarioContext.bookingId);
    }

    @When("User partially updates booking id {string} with data {string}")
    public void user_updates_invalid_booking(String invalidId, String dataKey) {
        ScenarioContext.response = given().spec(ApiClient.getSpec())
                .cookie("token", ScenarioContext.token)
                .body(JsonDataReader.getData(dataKey))
                .when()
                .patch("/booking/" + invalidId);
    }

    @When("User partially updates booking with invalid data:")
    public void user_updates_booking_invalid_data(DataTable table) {
        Map<String, String> data = table.asMap();
        ScenarioContext.response = given().spec(ApiClient.getSpec())
                .cookie("token", ScenarioContext.token)
                .body(data)
                .when()
                .patch("/booking/" + ScenarioContext.bookingId);
    }

    @When("User partially updates booking without authentication with data {string}")
    public void update_without_auth(String dataKey) {
        ScenarioContext.response = given().spec(ApiClient.getSpec())
                .body(JsonDataReader.getData(dataKey))
                .when()
                .patch("/booking/" + ScenarioContext.bookingId);
    }

    @When("User partially updates booking with data {string} twice")
    public void idempotent_update_twice(String dataKey) {
        ScenarioContext.response = given().spec(ApiClient.getSpec())
                .cookie("token", ScenarioContext.token)
                .body(JsonDataReader.getData(dataKey))
                .when()
                .patch("/booking/" + ScenarioContext.bookingId);
        Response secondResponse = given().spec(ApiClient.getSpec())
                .cookie("token", ScenarioContext.token)
                .body(JsonDataReader.getData(dataKey))
                .when()
                .patch("/booking/" + ScenarioContext.bookingId);
        assertEquals(ScenarioContext.response.asString(), secondResponse.asString());
    }

    @Then("Booking field {string} should be {string}")
    public void verify_field_updated(String field, String expected) {
        ScenarioContext.response.then().body(field, equalTo(expected));
    }

    @Then("Booking field {string} should remain unchanged as {string}")
    public void verify_field_unchanged(String field, String expected) {
        ScenarioContext.response.then().body(field, equalTo(expected));
    }

    @Then("Booking date {string} should be {string}")
    public void verify_nested_field(String field, String expected) {
        ScenarioContext.response.then().body("bookingdates." + field, equalTo(expected));
    }

    @Then("Both responses should be identical")
    public void verify_idempotency() {
        // already asserted in method
    }


}


