package example.cucumber;

import domain.AUserIsAlreadyLoggedInException;
import domain.UserIdDoesNotExistExeption;
import app.App;
import domain.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class LogWorkedTimeSteps {
    
    private App app;
    private ErrorMessageHolder errorMessage;
    private UserHelper userHelper;
    private ProjectHelper projectHelper;
    private ActivityHelper activityHelper;
    private Double doubleInput;
    private Double doublePlus = 0.0;
    public LogWorkedTimeSteps(App app, ErrorMessageHolder errorMessage,UserHelper userHelper,ProjectHelper p, ActivityHelper activityHelper){
        this.app = app;
        this.errorMessage = errorMessage;
        this.userHelper = userHelper;
        this.projectHelper=p;
        this.activityHelper = activityHelper;
    }
    @Given("the user has loggged {double} hours of work to the activity")
    public void userHasLoggedHours(Double double1) {
        User user = this.userHelper.getUser();
        this.doublePlus += double1;
        this.activityHelper.getActivity().logTime(double1, user);
    }
    @When("the user logs {double} hours of work to the activity")
    public void loggingTimeWorked(Double double1) throws UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException {
        User user = this.userHelper.getUser();
        this.doubleInput = double1;
        this.activityHelper.getActivity().logTime(double1, user);
    }
    @Then("the user has logged {double} hours of work to the activity")
    public void theUserHasTimeLogged(Double float1) {
        String userId = this.userHelper.getUser().getUserId();
        Double value = this.activityHelper.getActivity().getTimeMap().get(userId);
        Double expected = this.doublePlus + this.doubleInput;
        assertEquals(expected, value);
    }



}
