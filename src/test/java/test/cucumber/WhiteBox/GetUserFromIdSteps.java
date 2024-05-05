package test.cucumber.WhiteBox;

import app.App;
import domain.exceptions.UserIdAlreadyInUseExeption;
import domain.exceptions.UserIdDoesNotExistExeption;
import test.cucumber.helpers.UserHelper;
import test.cucumber.helpers.ErrorMessageHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import static org.junit.Assert.assertEquals;



public class GetUserFromIdSteps {
    private final UserHelper userHelper;
    private final App app;

    private final ErrorMessageHolder errorMessage;
    public GetUserFromIdSteps(UserHelper userHelper, App app, ErrorMessageHolder errorMessage) {
        this.userHelper = userHelper;
        this.app = app;
        this.errorMessage = errorMessage;
    }

    @When("the getUserFromId method is called with id {string}")
    public void theGetUserFromIDMethodIsCalledWithIdASGER(String string) throws UserIdDoesNotExistExeption {
        try {
            this.userHelper.setUser(app.getUserFromId(string));
        } catch (UserIdDoesNotExistExeption e) {
            this.errorMessage.setErrorMessage(e.getMessage());
        }
    }
    @Then("the user with id {string} is returned")
    public void theUserWithIdASGEIsReturned(String string) {
        assertEquals(string, userHelper.getUser().getUserId());
    }

    @Then("the exeption UserIdDoesNotExistException is thrown")
    public void theExeptionUserIdDoesNotExistExceptionIsThrown() {
        assertEquals("No user with UserId exists", errorMessage.getErrorMessage());
    }

    @Then("the exeption AUserIsAlreadyLoggedInException is thrown")
    public void theExeptionAUserIsAlreadyLoggedInExceptionIsThrown() {
        assertEquals("A user is already logged in", errorMessage.getErrorMessage());
    }

    @Given("no users exist")
    public void noUsersExist() {
        app.getUserList().clear();
    }


}
