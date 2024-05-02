package example.cucumber.steps;

import app.ActivityInfo;
import domain.exceptions.AUserIsAlreadyLoggedInException;
import domain.exceptions.UserIdDoesNotExistExeption;
import app.App;
import domain.Classes.User;
import example.cucumber.helpers.ErrorMessageHolder;
import example.cucumber.helpers.ProjectHelper;
import example.cucumber.helpers.UserHelper;
import example.cucumber.helpers.ActivityHelper;
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
        this.doublePlus += double1;
        app.logTimeOnActivity(doublePlus,this.activityHelper.getActivityInfo() );
    }
    @When("the user logs {double} hours of work to the activity")
    public void loggingTimeWorked(Double double1) throws UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException {
        app.logTimeOnActivity(double1,this.activityHelper.getActivityInfo());

    }
    @Then("the user has logged {double} hours of work to the activity")
    public void theUserHasTimeLogged(Double dob1) {
        ActivityInfo ai = app.getActivityInfoFromName(this.projectHelper.getProjectInfo(),this.activityHelper.getActivityInfo().getActivityName());
        assertEquals(ai.getTimeMap().get(this.userHelper.getUserInfo().getUserId()),dob1);

    }



}
