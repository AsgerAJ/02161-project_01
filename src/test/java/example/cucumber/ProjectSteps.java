package example.cucumber;

import domain.App;
import domain.DateServer;
import example.cucumber.ErrorMessageHolder;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mock;

public class ProjectSteps {
    private App app;
    private ErrorMessageHolder errorMessage;
    private MockDateHolder dateHolder;

    public ProjectSteps(App app, ErrorMessageHolder errorMessage, MockDateHolder dateHolder){
        this.app = app;
        this.errorMessage = errorMessage;
        this.dateHolder=dateHolder;
    }

    @When("the user creates a project with title {string}")
    public void theUserCreatesAProjectWithTitle(String string) {
        
    }
    @Then("the project is created in app")
    public void theProjectIsCreatedInApp() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the project is given the id {int}")
    public void theProjectIsGivenTheId(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the project is added to user projects.")
    public void theProjectIsAddedToUserProjects() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the user is added to the project participant list")
    public void theUserIsAddedToTheProjectParticipantList() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
