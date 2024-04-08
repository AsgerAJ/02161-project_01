package example.cucumber;

import domain.App;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class userSteps {
    private App app;

    public userSteps(App app){
        this.app = app;
    }

    @Given("no user with id {string} exists")
    public void no_user_with_id_exists(String string) {
        app.removeUserWithId(string);
        assertFalse(app.hasUserWithID(string));
    }
    @When("a user registers with user id {string}")
    public void a_user_registers_with_user_id(String string) {
        app.registerUser(string);
    }

    @Then("a user is registered with id {string}")
    public void a_user_is_registered_with_id(String string) {
        app.hasUserWithID(string);
    }

}
