package example.cucumber;

import app.UserIdAlreadyInUseExeption;
import app.UserIdDoesNotExistExeption;
import domain.App;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class LoginSteps {

    private App app;
    private ErrorMessageHolder errorMessage;

    public LoginSteps(App app, ErrorMessageHolder errorMessage){
        this.app = app;
        this.errorMessage = errorMessage;
    }
    @Given("no user is logged in")
    public void noUserIsLoggedIn() {
        app.logOut();
        assertFalse(app.loggedInStatus());
    }
    @When("logging in with id {string}")
    public void loggingInWithId(String string) throws UserIdDoesNotExistExeption {
        try {
            app.logInUser(string);
            assertTrue(app.loggedInStatus());
        } catch (UserIdDoesNotExistExeption e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }
    @Then("the current user has id {string}")
    public void theCurrentUserHasId(String string) {
        assertEquals(app.getCurrentUserId(), string);
    }

    @Given("a user with id {string} is logged in")
    public void aUserWithIdIsLoggedIn(String string) throws UserIdAlreadyInUseExeption, UserIdDoesNotExistExeption {
        if (!app.hasUserWithID(string)) {
            app.registerUser(string);
        }
        app.logInUser(string);
        assertTrue(app.loggedInStatus());
        assertEquals(string,app.getCurrentUserId());
    }
    @When("logging out")
    public void loggingOut() {
        app.logOut();
        assertFalse(app.loggedInStatus());
    }
    @Then("the user has logged out")
    public void theUserHasLoggedOut() {
        assertFalse(app.loggedInStatus());
    }


}
