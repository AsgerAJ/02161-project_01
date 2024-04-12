package example.cucumber;

import app.AUserIsAlreadyLoggedInException;
import app.UserIdAlreadyInUseExeption;
import app.UserIdDoesNotExistExeption;
import domain.App;
import domain.Project;
import domain.User;
import example.cucumber.ErrorMessageHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class LoginSteps {

    private App app;
    private ErrorMessageHolder errorMessage;
    private UserHelper userHelper;
    private ProjectHelper projectHelper;
    public LoginSteps(App app, ErrorMessageHolder errorMessage,UserHelper userHelper,ProjectHelper p){
        this.app = app;
        this.errorMessage = errorMessage;
        this.userHelper = userHelper;
        this.projectHelper=p;
    }
    @Given("no user is logged in")
    public void noUserIsLoggedIn() {
        app.logOut();
        assertFalse(app.loggedInStatus());
    }
    @When("logging in with id {string}")
    public void loggingInWithId(String string) throws UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException {
        try {
            app.logInUser(string);
            assertTrue(app.loggedInStatus());
        } catch (UserIdDoesNotExistExeption | AUserIsAlreadyLoggedInException e) {
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
        try {
            app.logInUser(string);
            assertTrue(app.loggedInStatus());
            assertEquals(string, app.getCurrentUserId());
        } catch (AUserIsAlreadyLoggedInException e) {
            this.errorMessage.setErrorMessage(e.getMessage());
        }
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
