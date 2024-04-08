package example.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class userSteps {
    public userSteps(){

    }
    @When("a user registers with user id {string}")
    public void a_user_registers_with_user_id(String string) {
        String userid = string;
    }
    @And("no user with id {string} exists")
    public void no_user_with_id_exists(String string) {

    }


    @Then("a user is registered with id {string}")
    public void a_user_is_registered_with_id(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
