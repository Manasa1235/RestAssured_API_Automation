package stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.ApiClient;
import utils.ScenarioContext;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

public class GetBookingStepDefs {


    @When("User requests all booking IDs without filters")
    public void user_requests_all_booking_ids_without_filters() {
        ScenarioContext.response = given().spec(ApiClient.getSpec())
                .when()
                .get("/booking");
    }

    @When("User requests bookings with filter {string}={string}")
    public void user_requests_bookings_with_individual_filter(String filter, String value) {
        ScenarioContext.response = given().spec(ApiClient.getSpec())
                .queryParam(filter, value)
                .when()
                .get("/booking");
    }

    @When("User requests bookings with filters:")
    public void user_requests_bookings_with_multiple_filters(DataTable table) {
        Map<String, String> filters = table.asMap();
        ScenarioContext.response = given().spec(ApiClient.getSpec())
                .queryParams(filters)
                .when()
                .get("/booking");
    }

    @Then("Booking response status should be {int}")
    public void booking_response_status_should_be(int statusCode) {
        int actualStatusCode = ScenarioContext.response.getStatusCode();
        try {
            // Perform assertion
            ScenarioContext.response.then().statusCode(statusCode);
            Hooks.test.pass(ScenarioContext.response.toString());
        } catch (AssertionError e) {
            Hooks.test.fail("Status code validation failed. Expected:" + statusCode + "but Actual Status code is :"+actualStatusCode);
        }

    }

    @Then("Booking response status should be {int} or {int}")
    public void booking_response_status_should_be_one_of(int status1, int status2) {
        int actualStatusCode = ScenarioContext.response.getStatusCode();
        try {
            // Perform assertion
            ScenarioContext.response.then().statusCode(anyOf(equalTo(status1), equalTo(status2)));
            Hooks.test.pass("Status code matched: " + actualStatusCode);
        } catch (AssertionError e) {
            Hooks.test.fail("Status code validation failed. Expected: " + status1 + " or " + status2 +
                    ", but got: " + actualStatusCode);
            Hooks.test.fail("<b>Response Body:</b><br><pre>" + ScenarioContext.response.getBody().asPrettyString() + "</pre>");
            throw e; // rethrow to fail the scenario
        }
    }

    @Then("Response should contain list of booking IDs")
    public void response_should_contain_list_of_booking_ids() {
        ScenarioContext.response.then()
                .body("", is(not(empty())))
                .body("bookingid", everyItem(isA(Integer.class)));

        List<Integer> bookingIds =  ScenarioContext.response.jsonPath().getList("bookingid");
        if (bookingIds == null || bookingIds.isEmpty()) {
            System.out.println("No bookings found for given filters.");
            Hooks.test.info("No bookings found for given filters.");
        } else {

            // Extract the full response body as a string
            String fullResponse =  ScenarioContext.response.getBody().asPrettyString();

            // Print to console
            System.out.println("Full API Response:\n" + fullResponse);

            // Log to Extent Reports
            Hooks.test.info("<b>Full API Response:</b><br><pre>" + fullResponse + "</pre>");
        }
    }

    @Then("Booking response time should be less than {int} milliseconds")
    public void booking_response_time_should_be_less_than(int timeMs) {
        ScenarioContext.response.then().time(lessThan((long) timeMs));
    }

}

