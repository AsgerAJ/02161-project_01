package example.cucumber;

import domain.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Calendar;

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
    @When("user creates an activity with name {string} with time budget {int}")
    public void userCreatesAnActivityWithNameWithTimeBudget(String string, Integer int1) {
        this.activityHelper.setActivity(new Activity(string, int1));
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



}
