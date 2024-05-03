package example.cucumber.steps;

import app.App;
import domain.Classes.Activity;
import domain.Classes.User;
import domain.exceptions.UserIdDoesNotExistExeption;
import example.cucumber.helpers.UserHelper;
import example.cucumber.helpers.ActivityHelper;
import example.cucumber.helpers.ErrorMessageHolder;
import example.cucumber.helpers.MockDateHolder;
import example.cucumber.helpers.ProjectHelper;
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
        assertTrue(this.app.hasProjectWithTitle(this.projectHelper.getProject().getName()));
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
        ArrayList< Activity > activityList = this.activityHelper.getExampleActivities(int1,int2,this.projectHelper.getProjectInfo().getProjectID());
        for (Activity a : activityList) {
            app.createNewActivity(a.getName(),a.getBudgetTime(),this.projectHelper.getProjectInfo());
        }

    }

    @Given("all the activities the project have start date {double}")
    public void allTheActivitiesTheProjectHaveStartDate(Double double1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the participant list contains {int} user with userID {string}")
    public void theParticipantListContainsUserWithUserID(Integer int1, String string) {
        ArrayList<User> participantList = this.projectHelper.getProject().getParticipantList();
        int count = 0;
        for (User u : participantList) {
            if (u.getUserId().equalsIgnoreCase(string)) {
                count++;
            }
        }
        assertTrue(int1==count);

    }

    @When("the user is removed from the project")
    public void removeUserFromProject() throws UserIdDoesNotExistExeption {
        app.removeUserFromProject(this.userHelper.getUserInfo().getUserId(),this.projectHelper.getProjectInfo());
    }

    @Then("the user is no longer part of the project")
    public void userNoLongerPartOfProject() {
        assertFalse(this.projectHelper.getProject().getParticipantList().contains(this.userHelper.getUser()));
    }

    @Given("the user marks the project as complete")
    public void theUserMarksTheProjectAsComplete() {
        app.changeCompletenessOfProject(true,this.projectHelper.getProjectInfo());
    }
    @When("the user marks the project as incomplete")
    public void theUserMarksTheProjectAsIncomplete() {
        app.changeCompletenessOfProject(false,this.projectHelper.getProjectInfo());
    }
    @Then("the project is not complete")
    public void theProjectIsNotComplete() {
        this.projectHelper.setProjectInfo(app.renewProjectInfo(projectHelper.getProjectInfo()));
        assertFalse(this.projectHelper.getProjectInfo().getComplete());
    }
    @Then("the project is marked as complete")
    public void theProjectIsMarkedAsComplete() {
        this.projectHelper.setProjectInfo(app.renewProjectInfo(projectHelper.getProjectInfo()));
        assertTrue(this.projectHelper.getProjectInfo().getComplete());
    }


}
