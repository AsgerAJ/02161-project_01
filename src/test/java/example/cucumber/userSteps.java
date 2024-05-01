package example.cucumber;

import domain.User;
import domain.UserIdAlreadyInUseExeption;
import domain.UserIdDoesNotExistExeption;
import app.App;
import domain.Leave;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class userSteps {
    private App app;
    private ErrorMessageHolder errorMessage;
    private UserHelper userHelper;
    private ProjectHelper projecthelper;
    public userSteps(App app, ErrorMessageHolder errorMessage,UserHelper userHelper,ProjectHelper p){
        this.app = app;
        this.errorMessage = errorMessage;
        this.userHelper = userHelper;
        this.projecthelper=p;
    }

    @Given("no user with id {string} exists")
    public void no_user_with_id_exists(String string) {
        app.removeUserWithId(string);
        assertFalse(app.hasUserWithID(string));
    }
    @When("a user registers with user id {string}")
    public void a_user_registers_with_user_id(String string) throws Exception {
        try {
            this.userHelper.setUser(this.app.registerUser(string));
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
        this.userHelper.setUser(this.app.registerUser(string));
        assertTrue(app.hasUserWithID(string));
    }
    @Then("the errormessage {string} is given")
    public void the_errormessage_is_given(String string) {
        assertEquals(string,errorMessage.getErrorMessage());
    }


    @Given("{int} users are part of the project")
    public void users_are_part_of_the_project(Integer int1) throws UserIdAlreadyInUseExeption {
        ArrayList<User> exampleUsers =this.userHelper.getExampleUsers(int1);

        for (User u : exampleUsers) {
            this.app.registerUser(u.getUserId());
            this.projecthelper.getProject().assignUser(u);
        }

    }



}
