package example.cucumber;

import domain.App;
import domain.DateServer;
import domain.Project;
import example.cucumber.ErrorMessageHolder;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProjectSteps {
    private App app;
    private ErrorMessageHolder errorMessage;
    private MockDateHolder dateHolder;

    private Project projectHelper;

    public ProjectSteps(App app, ErrorMessageHolder errorMessage, MockDateHolder dateHolder){
        this.app = app;
        this.errorMessage = errorMessage;
        this.dateHolder=dateHolder;
    }

    @When("the user creates a project with title {string}")
    public void theUserCreatesAProjectWithTitle(String string) {
        this.projectHelper=this.app.createProject(string);

    }
    @Then("the project is created in app")
    public void theProjectIsCreatedInApp() {
        assertTrue(this.app.hasProjectWithTitle(this.projectHelper.getTitle()));
    }
    @Then("the project is given the id {int}")
    public void theProjectIsGivenTheId(Integer int1) {
        assertEquals(this.projectHelper.getProjectID(), String.valueOf(int1));
    }
    @Then("the project is added to user projects.")
    public void theProjectIsAddedToUserProjects() {
       assertTrue( this.app.getCurrentUser().hasProjectWithID(this.projectHelper.getProjectID()));
    }
    @Then("the user is added to the project participant list")
    public void theUserIsAddedToTheProjectParticipantList() {
        assertTrue(this.projectHelper.getParticipanList().contains(this.app.getCurrentUser()));
    }

}
