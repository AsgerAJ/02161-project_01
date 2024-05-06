package test.cucumber.steps;

import domain.exceptions.UserIdAlreadyInUseExeption;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import app.App;
import test.cucumber.helpers.StringHelper;
import test.cucumber.helpers.ErrorMessageHolder;

import static org.junit.Assert.assertEquals;


public class createUserIdSteps {
    private App app;
    private ErrorMessageHolder errorMessageHolder;
    private StringHelper stringHelper;
    public createUserIdSteps(StringHelper stringhelper,ErrorMessageHolder errorMessageHolder, App app){
        this.app = app;
        this.errorMessageHolder = errorMessageHolder;
        this.stringHelper = stringhelper;
    }


    @When("the user enters {string} as the user name")
    public void theUserEntersAsTheUserName(String string) throws IllegalArgumentException {
        try {
            this.stringHelper.setString(app.createUserId(string));
        } catch (IllegalArgumentException e) {
            this.errorMessageHolder.setErrorMessage(e.getMessage());
        }


    }
    @Then("the user id {string} is created")
    public void theUserIdIsCreated(String string) {
        assertEquals(string,this.stringHelper.getString());
    }
}
