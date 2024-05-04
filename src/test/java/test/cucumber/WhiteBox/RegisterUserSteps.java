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
import static org.junit.Assert.assertTrue;

public class RegisterUserSteps {
    private final UserHelper userHelper;
    private final App app;

    private final ErrorMessageHolder errorMessage;
    public RegisterUserSteps(UserHelper userHelper, App app, ErrorMessageHolder errorMessage) {
        this.userHelper = userHelper;
        this.app = app;
        this.errorMessage = errorMessage;
    }

    @When("the registerUser method is called with id {string}")
    public void the_register_user_method_is_called_with_id(String string) throws UserIdAlreadyInUseExeption {
        this.userHelper.setUser(app.registerUser(string));
    }
    @Then("user with id {string} is added to the userList")
    public void user_with_id_is_added_to_the_user_list(String string) {
        assertTrue(app.getUserList().contains(this.userHelper.getUser()));
    }
}
