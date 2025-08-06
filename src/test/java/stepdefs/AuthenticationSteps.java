package stepdefs;

import io.cucumber.java.en.Given;
import utils.AuthenticationHelper;
import utils.ScenarioContext;

public class AuthenticationSteps {

    @Given("User has valid auth token")
    public void user_has_valid_auth_token() {
        ScenarioContext.token = AuthenticationHelper.getToken();
        System.out.println("Generated Token: " + ScenarioContext.token);
    }

}
