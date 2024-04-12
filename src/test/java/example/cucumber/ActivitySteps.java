package example.cucumber;

import domain.Activity;
import domain.App;
import domain.Date;
import domain.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Calendar;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
    @When("user creates an activity with name {string} with start date {double} and end date {double} and with time budget {int}")
    public void userCreatesAnActivityWithNameWithStartDateAndEndDateAndWithTimeBudget(String string, Double double1, Double double2, Integer int1) {
        this.activityHelper.setActivity(new Activity(string, new Date(double1), new Date(double2), int1));
        this.projectHelper.getProject().createNewActivity(this.activityHelper.getActivity());
    }

    @Then("the activity with name {string} is added to the project")
    public void theActivityIsAddedToTheProject(String string) {
        assertTrue(this.projectHelper.getProject().getActivityList().contains(this.activityHelper.getActivity()));
    }

}
