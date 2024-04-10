package example.cucumber;

import app.UserIdAlreadyInUseExeption;
import domain.App;
import example.cucumber.ErrorMessageHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class userSteps {
    private App app;
    private ErrorMessageHolder errorMessage;

    public userSteps(App app, ErrorMessageHolder errorMessage){
        this.app = app;
        this.errorMessage = errorMessage;
    }

    @Given("no user with id {string} exists")
    public void no_user_with_id_exists(String string) {
        app.removeUserWithId(string);
        assertFalse(app.hasUserWithID(string));
    }
    @When("a user registers with user id {string}")
    public void a_user_registers_with_user_id(String string) throws Exception {
        try {
            app.registerUser(string);
            assertTrue(app.hasUserWithID(string));
        }catch (UserIdAlreadyInUseExeption e){
            errorMessage.setErrorMessage(e.getMessage());
        }

    }

    @Then("a user is registered with id {string}")
    public void a_user_is_registered_with_id(String string) {
        assertTrue(app.hasUserWithID(string));
    }

    @Given("a user with id {string} exists")
    public void a_user_with_id_exists(String string) throws Exception{
        app.registerUser(string);
        assertTrue(app.hasUserWithID(string));
    }
    @Then("the errormessage {string} is given")
    public void the_errormessage_is_given(String string) {
        assertEquals(string,errorMessage.getErrorMessage());
    }

}
