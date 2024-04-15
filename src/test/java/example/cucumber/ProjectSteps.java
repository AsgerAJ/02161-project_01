package example.cucumber;

import domain.App;
import domain.Project;
import domain.User;
import example.cucumber.ErrorMessageHolder;
import example.cucumber.MockDateHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.GregorianCalendar;

import static java.util.Calendar.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProjectSteps {
    private App app;
    private ErrorMessageHolder errorMessage;
    private MockDateHolder dateHolder;

    private UserHelper userHelper;
    private ProjectHelper projectHelper;
    public ProjectSteps(App app, ErrorMessageHolder errorMessage, MockDateHolder dateHolder,UserHelper userHelper,ProjectHelper p){
        this.app = app;
        this.errorMessage = errorMessage;
        this.dateHolder=dateHolder;
        this.userHelper=userHelper;
        this.projectHelper=p;

    }

    @When("the user creates a project with title {string}")
    public void theUserCreatesAProjectWithTitle(String string) {
        this.projectHelper.setProject(this.app.createProject(string));

    }
    @Then("the project is created in app")
    public void theProjectIsCreatedInApp() {
        assertTrue(this.app.hasProjectWithTitle(this.projectHelper.getProject().getTitle()));
    }
    @Then("the project is given the id {int}")
    public void theProjectIsGivenTheId(Integer int1) {
        assertEquals(this.projectHelper.getProject().getProjectID(), String.valueOf(int1));
    }
    @Then("the project is added to user projects.")
    public void theProjectIsAddedToUserProjects() {
       assertTrue( this.userHelper.getUser().hasProjectWithID(this.projectHelper.getProject().getProjectID()));
    }
    @Then("the user is added to the project participant list")
    public void theUserIsAddedToTheProjectParticipantList() {
        assertTrue(this.projectHelper.getProject().getParticipantList().contains(this.app.getCurrentUser()));
    }

    @Given("the user with id {string} is project leader")
    public void theUserIsProjectLeader(String string) {
        this.projectHelper.getProject().setProjectLeader(userHelper.getUser());
        assertTrue(this.projectHelper.getProject().isProjectLeader(userHelper.getUser()));
    }

    @When("sets the deadline of the project to {int},{int},{int}")
    public void setsTheDeadlineOfTheProjectTo(Integer day, Integer month, Integer year) {
        this.projectHelper.getProject().setDeadline(new GregorianCalendar(year,month,day));

    }
    @Then("the project has the deadline {int},{int},{int}")
    public void theProjectHasTheDeadline(Integer day, Integer month, Integer year) {
        assertEquals((int) this.projectHelper.getProject().getDeadline().get(DAY_OF_MONTH), (int) day); //check same day
        assertEquals((int) this.projectHelper.getProject().getDeadline().get(MONTH), (int) month); //check same month
        assertEquals((int) this.projectHelper.getProject().getDeadline().get(YEAR), (int) year); //check same year
    }
    @Given("the project has been given the deadline {int},{int},{int}")
    public void theProjectHasBeenGivenTheDeadline(Integer day,Integer month,Integer year) {
        this.projectHelper.getProject().setDeadline(new GregorianCalendar(year,month,day));
        assertEquals((int) this.projectHelper.getProject().getDeadline().get(DAY_OF_MONTH), (int) day); //check same day
        assertEquals((int) this.projectHelper.getProject().getDeadline().get(MONTH), (int) month); //check same month
        assertEquals((int) this.projectHelper.getProject().getDeadline().get(YEAR), (int) year); //check same year

    }
    @Then("the project is overdue")
    public void theProjectIsOverdue() {
        assertTrue(this.projectHelper.getProject().isOverdue(this.dateHolder.getDate()));
    }

}
