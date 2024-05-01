package example.cucumber.steps;

import app.App;
import domain.*;
import example.cucumber.helpers.ErrorMessageHolder;
import example.cucumber.helpers.ProjectHelper;
import example.cucumber.helpers.UserHelper;
import example.cucumber.helpers.ActivityHelper;
import example.cucumber.helpers.MockDateHolder;
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
        this.activityHelper.setActivity(new Activity(string, dob1));
        this.projectHelper.getProject().createNewActivity(this.activityHelper.getActivity());
    }

    @Then("the activity with name {string} is added to the project")
    public void theActivityIsAddedToTheProject(String string) {
        assertTrue(this.projectHelper.getProject().getActivityList().contains(this.activityHelper.getActivity()));
    }

    @Given("the user is part of the project")
    public void theUserIsPartOfTheProject() {
        this.projectHelper.getProject().assignUser(this.userHelper.getUser());
        assertTrue(this.projectHelper.getProject().getParticipantList().contains(this.userHelper.getUser()));
    }

    @Given("the project has an activity with name {string}")
    public void theProjectHasAnActivity(String string) {
        this.activityHelper.setActivity(new Activity(string, 1));
        this.projectHelper.getProject().createNewActivity(this.activityHelper.getActivity());
        assertTrue(this.projectHelper.getProject().getActivityList().contains(this.activityHelper.getActivity()));
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
        assertTrue(dob1==this.activityHelper.getActivity().getBudgetTime());
    }
    @When("the user is added to the activity")
    public void theUserIsAddedToTheActivity() {
        this.activityHelper.getActivity().assignUser(this.userHelper.getUser());
    }
    @Then("the user is part of the activity")
    public void theUserIsPartOfTheActivity() {
        assertTrue(this.activityHelper.getActivity().getParticipantList().contains(this.userHelper.getUser()));
    }
    @Given("the user is assigned the activity")
    public void theUserIsAssignedTheActivity(){
        this.activityHelper.getActivity().assignUser(this.userHelper.getUser());
    }

    @When("the user is removed from the activity")
    public void removeUserFromActivity() {
        this.activityHelper.getActivity().removeUser(this.userHelper.getUser());
    }

    @Then("the user is no longer part of the activity")
    public void userNoLongerPartOfActivity() {
        assertFalse(this.activityHelper.getActivity().getParticipantList().contains(this.userHelper.getUser()));
    }
}
