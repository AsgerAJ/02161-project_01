package test.cucumber.steps;

import app.ActivityInfo;
import domain.exceptions.AUserIsAlreadyLoggedInException;
import domain.exceptions.UserIdDoesNotExistExeption;
import app.App;
import test.cucumber.helpers.ProjectHelper;
import test.cucumber.helpers.UserHelper;
import test.cucumber.helpers.ActivityHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class LogWorkedTimeSteps {
    
    private App app;
    private UserHelper userHelper;
    private ProjectHelper projectHelper;
    private ActivityHelper activityHelper;
    private Double doublePlus = 0.0;
    public LogWorkedTimeSteps(App app, UserHelper userHelper,ProjectHelper p, ActivityHelper activityHelper){
        this.app = app;
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
    @Then("the activity is overbudget")
    public void theActivityIsOverbudget() {
        this.activityHelper.setActivityInfo(app.renewActivityInfo(this.activityHelper.getActivityInfo()));
        assertTrue(app.isActivityOverBudget(this.activityHelper.getActivityInfo()));
    }


}
