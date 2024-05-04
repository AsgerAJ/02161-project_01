package test.cucumber.steps;

import domain.Classes.Activity;
import domain.exceptions.InvalidDateException;
import app.App;
import test.cucumber.helpers.UserHelper;
import test.cucumber.helpers.ActivityHelper;
import test.cucumber.helpers.ErrorMessageHolder;
import test.cucumber.helpers.MockDateHolder;
import test.cucumber.helpers.ProjectHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.util.Calendar.*;
import static java.util.Calendar.YEAR;
import static org.junit.Assert.*;

public class StartDate_DeadlineSteps {
    private App app;
    private ErrorMessageHolder errorMessage;
    private MockDateHolder dateHolder;


    private UserHelper userHelper;
    private ProjectHelper projectHelper;
    private ActivityHelper activityHelper;

    public StartDate_DeadlineSteps(App app, ErrorMessageHolder errorMessage, MockDateHolder dateHolder,
                                   UserHelper userHelper, ProjectHelper p,ActivityHelper a) {
        this.app = app;
        this.errorMessage = errorMessage;
        this.dateHolder = dateHolder;
        this.userHelper = userHelper;
        this.projectHelper = p;
        this.activityHelper = a;

    }


    @When("sets the deadline of the project to {int},{int},{int}")
    public void setsTheDeadlineOfTheProjectTo(Integer day, Integer month, Integer year) throws InvalidDateException {
       try {
           this.app.setProjectDeadline(new GregorianCalendar(year, month-1, day),this.projectHelper.getProjectInfo());
       } catch (InvalidDateException e) {
           this.errorMessage.setErrorMessage(e.getMessage());
       }

    }

    @Then("the project has the deadline {int},{int},{int}")
    public void theProjectHasTheDeadline(Integer day, Integer month, Integer year) {
        this.projectHelper.setProjectInfo(app.renewProjectInfo(this.projectHelper.getProjectInfo()));
        assertEquals((int) this.projectHelper.getProjectInfo().getDeadlineCopy().get(DAY_OF_MONTH), (int) day); //check same day
        assertEquals((int) this.projectHelper.getProjectInfo().getDeadlineCopy().get(MONTH), (int) month-1); //check same month
        assertEquals((int) this.projectHelper.getProjectInfo().getDeadlineCopy().get(YEAR), (int) year); //check same year
    }

    @Given("the project has been given the deadline {int},{int},{int}")
    public void theProjectHasBeenGivenTheDeadline(Integer day, Integer month, Integer year) throws InvalidDateException {
        app.setProjectDeadline(new GregorianCalendar(year, month-1, day),this.projectHelper.getProjectInfo());
        this.projectHelper.setProjectInfo(app.renewProjectInfo(this.projectHelper.getProjectInfo()));
        assertEquals((int) this.projectHelper.getProject().getDeadline().get(DAY_OF_MONTH), (int) day); //check same day
        assertEquals((int) this.projectHelper.getProject().getDeadline().get(MONTH), (int) month -1 ); //check same month
        assertEquals((int) this.projectHelper.getProject().getDeadline().get(YEAR), (int) year); //check same year

    }

    @Then("the project is overdue")
    public void theProjectIsOverdue() {
        assertTrue(app.isProjectOverdue(this.projectHelper.getProjectInfo()));
    }
    @Then("the project is not overdue")
    public void the_project_is_not_overdue() {
        assertFalse(app.isProjectOverdue(this.projectHelper.getProjectInfo()));
    }

    @When("sets the startdate of the project to {int},{int},{int}")
    public void setsTheStartdateOfTheProjectTo(Integer day, Integer month, Integer year) throws InvalidDateException {
        try {
            this.app.setProjectStartDate(new GregorianCalendar(year, month - 1, day),this.projectHelper.getProjectInfo());
        } catch (InvalidDateException e) {
            this.errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the project has the startdate {int},{int},{int}")
    public void theProjectHasTheStartdate(Integer day, Integer month, Integer year) {
        this.projectHelper.setProjectInfo(app.renewProjectInfo(this.projectHelper.getProjectInfo()));

        assertEquals((int) this.projectHelper.getProjectInfo().getStartdateCopy().get(DAY_OF_MONTH), (int) day); //check same day
        assertEquals((int) this.projectHelper.getProjectInfo().getStartdateCopy().get(MONTH), (int) month-1); //check same month
        assertEquals((int) this.projectHelper.getProjectInfo().getStartdateCopy().get(YEAR), (int) year); //check same year

    }



    @Given("all the activities have start date {int},{int},{int}")
    public void allTheActivitiesHaveStartDate(Integer day,Integer month, Integer year) throws InvalidDateException {
        Calendar date = new GregorianCalendar(year,month-1,day);
        ArrayList<Activity> actList = this.activityHelper.getExampleActivityList();
        for (Activity act : actList) { app.setActivityStartDateFromInfo(act.asInfo(),date);}
        for (Activity act : actList) {
            assertEquals((int) day, app.renewActivityInfo(act.asInfo()).getStartdateCopy().get(Calendar.DAY_OF_MONTH));
            assertEquals((int) month - 1, app.renewActivityInfo(act.asInfo()).getStartdateCopy().get(MONTH)); //-1 to compensate for 0 indexing
            assertEquals((int) year,app.renewActivityInfo(act.asInfo()).getStartdateCopy().get(Calendar.YEAR));
        }
    }

    @Given("all the activities have deadline {int},{int},{int}")
    public void allTheActivitiesHaveDeadline(Integer day, Integer month, Integer year) throws InvalidDateException {
        Calendar date = new GregorianCalendar(year,month-1,day);
        ArrayList<Activity> actList = this.activityHelper.getExampleActivityList();
        for (Activity act : actList) { app.setActivityDeadlineFromInfo(act.asInfo(),date);}
        for (Activity act : actList) {
            assertEquals((int) day, app.renewActivityInfo(act.asInfo()).getDeadlineCopy().get(DAY_OF_MONTH));
            assertEquals((int) month - 1, app.renewActivityInfo(act.asInfo()).getDeadlineCopy().get(MONTH)); //-1 to compensate for 0 indexing
            assertEquals((int) year,app.renewActivityInfo(act.asInfo()).getDeadlineCopy().get(Calendar.YEAR));
        }

    }

    @Given("the activity has start date {int},{int},{int}")
    public void theActivityHasStartDate(Integer day, Integer month, Integer year) throws InvalidDateException {
        Calendar c = new GregorianCalendar(year,month-1,day);
        app.setActivityStartDateFromInfo(this.activityHelper.getActivityInfo(),c);
    }
    @Given("the activity has deadline {int},{int},{int}")
    public void theActivityHasDeadline(Integer day, Integer month, Integer year) throws InvalidDateException {
        Calendar c = new GregorianCalendar(year,month-1,day);
        app.setActivityDeadlineFromInfo(this.activityHelper.getActivityInfo(),c);

    }

    @When("user sets activity startdate to {int},{int},{int}")
    public void userSetsActivityStartdateTo(Integer day,Integer month, Integer year) throws InvalidDateException {
        Calendar c = new GregorianCalendar(year,month-1,day);
        app.setActivityStartDateFromInfo(this.activityHelper.getActivityInfo(),c);
    }

    @When("user sets activity deadline to {int},{int},{int}")
    public void userSetsActivityDeadlineTo(Integer day,Integer month, Integer year) throws InvalidDateException {
        Calendar c = new GregorianCalendar(year,month-1,day);
        app.setActivityDeadlineFromInfo(this.activityHelper.getActivityInfo(),c);

    }


    @Given("the project has not been given a deadline")
    public void theProjectHasNotBeenGivenADeadline() throws InvalidDateException {
        app.setProjectDeadline(null,this.projectHelper.getProjectInfo());
        this.projectHelper.setProjectInfo(app.renewProjectInfo(this.projectHelper.getProjectInfo()));
    }

    @Given("the activity is not set to complete")
    public void theActivityIsNotSetToComplete() {
        this.activityHelper.setActivityInfo(app.renewActivityInfo(this.activityHelper.getActivityInfo()));
        app.ChangeCompletenessOfActivity(false,this.activityHelper.getActivityInfo());
        this.activityHelper.setActivityInfo(app.renewActivityInfo(this.activityHelper.getActivityInfo()));
    }
    @Then("the activity is overdue")
    public void theActivityIsOverdue() {
        assertTrue(app.isActivityOverdue(this.activityHelper.getActivityInfo()));
    }

    @Then("the activity is not overdue")
    public void theActivityIsNotOverdue() {
        assertFalse(app.isActivityOverdue(this.activityHelper.getActivityInfo()));
    }


}
