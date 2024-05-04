package test.cucumber.steps;

import app.App;
import app.ProjectInfo;
import domain.exceptions.UserIdDoesNotExistExeption;
import test.cucumber.helpers.ErrorMessageHolder;
import test.cucumber.helpers.ProjectHelper;
import test.cucumber.helpers.UserHelper;
import test.cucumber.helpers.ActivityHelper;
import test.cucumber.helpers.MockDateHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;



import static org.junit.Assert.*;

public class ActivitySteps {
    private App app;
    private ErrorMessageHolder errorMessage;
    private MockDateHolder dateHolder;

    private UserHelper userHelper;
    private ProjectHelper projectHelper;
    private ActivityHelper activityHelper;
    public ActivitySteps(App app, ErrorMessageHolder errorMessage, MockDateHolder dateHolder,UserHelper userHelper,ProjectHelper p, ActivityHelper activityHelper){
        this.app = app;
        this.errorMessage = errorMessage;
        this.dateHolder=dateHolder;
        this.userHelper=userHelper;
        this.projectHelper=p;
        this.activityHelper = activityHelper;
    }

    @Given("a project exists with title {string}")
    public void aProjectExists(String string) {
        this.projectHelper.setProject(this.app.createProject(string));
        assertNotNull(this.projectHelper.getProject());
    }
    @When("user creates an activity with name {string} with time budget {double}")
    public void userCreatesAnActivityWithNameWithTimeBudget(String string, Double dob1) {
        app.createNewActivity(string,dob1,app.getProjectInfoFromID(this.projectHelper.getProjectInfo().getProjectID()));
        this.activityHelper.setActivityInfo(app.getActivityInfoFromName(this.projectHelper.getProjectInfo(),string));

    }

    @Then("the activity with name {string} is added to the project")
    public void theActivityIsAddedToTheProject(String string) {
        ProjectInfo pi = app.getProjectInfoFromID(this.projectHelper.getProjectInfo().getProjectID());
        assertTrue(pi.getActivityList().contains(string));
    }

    @Given("the user is part of the project")
    public void theUserIsPartOfTheProject() {
        this.projectHelper.getProject().assignUser(this.userHelper.getUser());
        assertTrue(this.projectHelper.getProject().getParticipantList().contains(this.userHelper.getUser()));
    }

    @Given("the project has an activity with name {string}")
    public void theProjectHasAnActivity(String string) {
        app.createNewActivity(string,1,this.projectHelper.getProjectInfo());
        this.activityHelper.setActivityInfo(app.getActivityInfoFromName(this.projectHelper.getProjectInfo(),string));
        assertTrue(app.getProjectInfoFromID(this.projectHelper.getProjectInfo().getProjectID()).getActivityList().contains(string));
    }
    @When("user marks activity {string} as complete")
    public void userMarksActivityAsComplete(String string) {
        this.projectHelper.getProject().getActivityFromName(string).setStatus(true);
    }
    @Then("the activity with name {string} is marked as completed")
    public void theActivityIsMarkedAsCompleted(String string) {
        assertTrue(this.projectHelper.getProject().getActivityFromName(string).getStatus());
    }
    @Then("the activity with name {string} has budget {double}")
    public void theActivityWithNameHasBudget(String string, Double dob1) {

        assertTrue(dob1==app.getActivityInfoFromName(this.projectHelper.getProjectInfo(),string).getBudgetTime());
    }
    @When("the user is added to the activity")
    public void theUserIsAddedToTheActivity() throws UserIdDoesNotExistExeption {
        app.assignUserToActivity(this.userHelper.getUserInfo().getUserId(),this.activityHelper.getActivityInfo());
    }
    @Then("the user is part of the activity")
    public void theUserIsPartOfTheActivity() {
        assertTrue(this.activityHelper.getActivityInfo().getParticipantList().contains(this.userHelper.getUserInfo().getUserId()));
    }
    @Given("the user is assigned the activity")
    public void theUserIsAssignedTheActivity() throws UserIdDoesNotExistExeption {
        app.assignUserToActivity(this.userHelper.getUserInfo().getUserId(),this.activityHelper.getActivityInfo());
    }

    @When("the user is removed from the activity")
    public void removeUserFromActivity() throws UserIdDoesNotExistExeption {
        app.removeUserFromActivity(userHelper.getUserInfo().getUserId(),this.activityHelper.getActivityInfo());
    }

    @Then("the user is no longer part of the activity")
    public void userNoLongerPartOfActivity() {
        this.activityHelper.setActivityInfo( app.renewActivityInfo(this.activityHelper.getActivityInfo()));
        assertFalse(this.activityHelper.getActivityInfo().getParticipantList().contains(this.userHelper.getUser().getUserId()));
    }

    @Given("the user marks the activity as complete")
    public void theUserMarksTheActivityAsComplete() {
        this.activityHelper.setActivityInfo(app.renewActivityInfo(this.activityHelper.getActivityInfo()));
        app.ChangeCompletenessOfActivity(true,this.activityHelper.getActivityInfo());
    }
    @When("the user uncompletes the activity")
    public void theUserUncompletesTheActivity() {
        app.ChangeCompletenessOfActivity(false,this.activityHelper.getActivityInfo());
    }
    @Then("the activity is not complete")
    public void theActivityIsNotComplete() {
        this.activityHelper.setActivityInfo(app.renewActivityInfo(this.activityHelper.getActivityInfo()));
        assertFalse(this.activityHelper.getActivityInfo().isComplete());
    }



}
