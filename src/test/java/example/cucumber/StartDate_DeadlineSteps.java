package example.cucumber;

import app.InvalidDateException;
import domain.App;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.GregorianCalendar;

import static java.util.Calendar.*;
import static java.util.Calendar.YEAR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StartDate_DeadlineSteps {
    private App app;
    private ErrorMessageHolder errorMessage;
    private MockDateHolder dateHolder;

    private UserHelper userHelper;
    private ProjectHelper projectHelper;


    public StartDate_DeadlineSteps(App app, ErrorMessageHolder errorMessage, MockDateHolder dateHolder, UserHelper userHelper, ProjectHelper p) {
        this.app = app;
        this.errorMessage = errorMessage;
        this.dateHolder = dateHolder;
        this.userHelper = userHelper;
        this.projectHelper = p;

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

}
