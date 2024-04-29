package example.cucumber;

import domain.Activity;
import domain.InvalidDateException;
import app.App;
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
           this.projectHelper.getProject().setDeadline(new GregorianCalendar(year, month-1, day));
       } catch (InvalidDateException e) {
           this.errorMessage.setErrorMessage(e.getMessage());
       }

    }

    @Then("the project has the deadline {int},{int},{int}")
    public void theProjectHasTheDeadline(Integer day, Integer month, Integer year) {
        assertEquals((int) this.projectHelper.getProject().getDeadline().get(DAY_OF_MONTH), (int) day); //check same day
        assertEquals((int) this.projectHelper.getProject().getDeadline().get(MONTH), (int) month-1); //check same month
        assertEquals((int) this.projectHelper.getProject().getDeadline().get(YEAR), (int) year); //check same year
    }

    @Given("the project has been given the deadline {int},{int},{int}")
    public void theProjectHasBeenGivenTheDeadline(Integer day, Integer month, Integer year) throws InvalidDateException {
        this.projectHelper.getProject().setDeadline(new GregorianCalendar(year, month-1, day));
        assertEquals((int) this.projectHelper.getProject().getDeadline().get(DAY_OF_MONTH), (int) day); //check same day
        assertEquals((int) this.projectHelper.getProject().getDeadline().get(MONTH), (int) month -1 ); //check same month
        assertEquals((int) this.projectHelper.getProject().getDeadline().get(YEAR), (int) year); //check same year

    }

    @Then("the project is overdue")
    public void theProjectIsOverdue() {
        assertTrue(this.projectHelper.getProject().isOverdue(this.dateHolder.getDate()));
    }
    @Then("the project is not overdue")
    public void the_project_is_not_overdue() {
        assertFalse(this.projectHelper.getProject().isOverdue(this.dateHolder.getDate()));
    }

    @When("sets the startdate of the project to {int},{int},{int}")
    public void setsTheStartdateOfTheProjectTo(Integer day, Integer month, Integer year) throws InvalidDateException {
        try {
            this.projectHelper.getProject().setStartDate(new GregorianCalendar(year, month - 1, day));
        } catch (InvalidDateException e) {
            this.errorMessage.setErrorMessage(e.getMessage());
        }
    }

    @Then("the project has the startdate {int},{int},{int}")
    public void theProjectHasTheStartdate(Integer day, Integer month, Integer year) {
        assertEquals((int) this.projectHelper.getProject().getStartDate().get(DAY_OF_MONTH), (int) day); //check same day
        assertEquals((int) this.projectHelper.getProject().getStartDate().get(MONTH), (int) month-1); //check same month
        assertEquals((int) this.projectHelper.getProject().getStartDate().get(YEAR), (int) year); //check same year

    }



    @Given("all the activities have start date {int},{int},{int}")
    public void allTheActivitiesHaveStartDate(Integer day,Integer month, Integer year) {
        Calendar date = new GregorianCalendar(year,month-1,day);
        ArrayList<Activity> actList = this.activityHelper.getExampleActivityList();
        for (Activity act : actList) { act.setStartdate(date);}
        for (Activity act : actList) {
            assertEquals((int) day, act.getStartdate().get(DAY_OF_MONTH));
            assertEquals((int) month - 1, act.getStartdate().get(MONTH)); //-1 to compensate for 0 indexing
            assertEquals((int) year,act.getStartdate().get(Calendar.YEAR));
        }
    }

    @Given("all the activities have deadline {int},{int},{int}")
    public void allTheActivitiesHaveDeadline(Integer day, Integer month, Integer year) {
        Calendar date = new GregorianCalendar(year,month-1,day);
        ArrayList<Activity> actList = this.activityHelper.getExampleActivityList();
        for (Activity act : actList) { act.setDeadline(date);}
        for (Activity act : actList) {
            assertEquals((int) day, act.getDeadline().get(DAY_OF_MONTH));
            assertEquals((int) month - 1, act.getDeadline().get(MONTH)); //-1 to compensate for 0 indexing
            assertEquals((int) year,act.getDeadline().get(Calendar.YEAR));
        }

    }

    @Given("the activity has start date {int},{int},{int}")
    public void theActivityHasStartDate(Integer day, Integer month, Integer year) {
        this.activityHelper.getActivity().setStartdate(new GregorianCalendar(year,month,day));
    }
    @Given("the activity has deadline {int},{int},{int}")
    public void theActivityHasDeadline(Integer day, Integer month, Integer year) {
        this.activityHelper.getActivity().setDeadline(new GregorianCalendar(year,month,day));
    }

}
