package example.cucumber;

import app.App;
import domain.Activity;
import domain.User;
import domain.UserIdDoesNotExistExeption;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ProjectSteps {
    private App app;
    private ErrorMessageHolder errorMessage;
    private MockDateHolder dateHolder;

    private UserHelper userHelper;
    private ProjectHelper projectHelper;
    private ActivityHelper activityHelper;
    public ProjectSteps(App app, ErrorMessageHolder errorMessage, MockDateHolder dateHolder,UserHelper userHelper,ProjectHelper p,ActivityHelper a){
        this.app = app;
        this.errorMessage = errorMessage;
        this.dateHolder=dateHolder;
        this.userHelper=userHelper;
        this.projectHelper=p;
        this.activityHelper=a;

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
    @Given("user {string} is part of the project")
    public void user_is_part_of_the_project(String string) throws UserIdDoesNotExistExeption {
        assertTrue(this.projectHelper.getProject().getParticipantList().contains(this.app.getUserFromId(string)));
    }
    @Given("user {string} is not part of the project")
    public void user_is_not_part_of_the_project(String string) throws UserIdDoesNotExistExeption {
        assertFalse(this.projectHelper.getProject().getParticipantList().contains(this.app.getUserFromId(string)));
    }
    @When("user {string} add's user {string} to the project")
    public void user_add_s_user_to_the_project(String string, String string2) throws UserIdDoesNotExistExeption {
        assertEquals(this.app.getCurrentUserId(), string);
        try { this.projectHelper.getProject().assignUser(this.app.getUserFromId(string2)); }
        catch (UserIdDoesNotExistExeption e ) {
            this.errorMessage.setErrorMessage(e.getMessage());
        }
    }
    @Then("the user {string} is added to the project participant list")
    public void user_get_s_assigned_to_project(String string) throws UserIdDoesNotExistExeption {
        assertTrue(this.projectHelper.getProject().getParticipantList().contains(this.app.getUserFromId(string)));
    }
    @Given("the project has {int} activities with budget time {int}")
    public void theProjectHasActivitiesWithBudgetTime(Integer int1,Integer int2) {
        ArrayList< Activity > activityList = this.activityHelper.getExampleActivities(int1,int2);
        for (Activity a : activityList) {
            this.projectHelper.getProject().createNewActivity(a);
        }

    }

    @Given("all the activities the project have start date {double}")
    public void allTheActivitiesTheProjectHaveStartDate(Double double1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }


}
