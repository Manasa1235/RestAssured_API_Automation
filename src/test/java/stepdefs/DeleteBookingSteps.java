package stepdefs;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.ApiClient;
import utils.ScenarioContext;

import static io.restassured.RestAssured.given;

public class DeleteBookingSteps {

    @When("User deletes the booking")
    public void delete_booking() {
        ScenarioContext.response = given().spec(ApiClient.getSpec())
                .cookie("token", ScenarioContext.token)
                .when()
                .delete("/booking/" + ScenarioContext.bookingId);
    }

    @Then("Booking delete response status should be {int}")
    public void delete_response_status(int statusCode) {
        ScenarioContext.response.then().statusCode(statusCode);
    }

    @Then("Booking should no longer exist")
    public void verify_booking_deleted() {
        given().spec(ApiClient.getSpec())
                .when()
                .get("/booking/" + ScenarioContext.bookingId)
                .then()
                .statusCode(404);
    }

    @When("User deletes booking id {string}")
    public void delete_nonexistent_booking(String invalidId) {
        ScenarioContext.response = given().spec(ApiClient.getSpec())
                .cookie("token", ScenarioContext.token)
                .when()
                .delete("/booking/" + invalidId);
    }

    @When("User deletes booking without authentication")
    public void delete_without_auth() {
        ScenarioContext.response = given().spec(ApiClient.getSpec())
                .when()
                .delete("/booking/" + ScenarioContext.bookingId);
    }

}
