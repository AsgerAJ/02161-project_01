package example.cucumber;

import app.App;
import domain.Activity;
import domain.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

public class FindFreeEmployeeSteps {

    private App app;
    private ErrorMessageHolder errorMessage;
    private MockDateHolder dateHolder;
    private UserHelper userHelper;
    private ProjectHelper projectHelper;
    private ActivityHelper activityHelper;

    public FindFreeEmployeeSteps(App app, ErrorMessageHolder errorMessage, MockDateHolder dateHolder, UserHelper userHelper, ProjectHelper p, ActivityHelper a){
        this.app = app;
        this.errorMessage = errorMessage;
        this.dateHolder=dateHolder;
        this.userHelper=userHelper;
        this.projectHelper=p;
        this.activityHelper=a;
    }

    @Given("the users have different amounts of activities")
    public void theUsersHaveDifferentAmountsOfActivities() {
        ArrayList<Activity> actList = this.activityHelper.getExampleActivityList();
        ArrayList<User> userList=this.userHelper.getExampleUsersList();
        for (int actIndex = 0; actIndex<actList.size(); actIndex++) { //activity 1 to 1 users, activity 2 to 2 users...
            for (int userIndex=0; userIndex<actIndex; userIndex++) {
                if (userIndex<userList.size()) {
                    actList.get(actIndex).assignUser(userList.get(userIndex));
                }
            }
        }


    }
    @When("the user searches for free employee")
    public void theUserSearchesForFreeEmployee() {
        this.userHelper.setAvailableUserList(this.projectHelper.getProject().findFreeEmployee(this.activityHelper.getActivity()));
    }
    @Then("all users are returned")
    public void allUsersAreReturned() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the returned users are sorted by amount of activities overlapping")
    public void theReturnedUsersAreSortedByAmountOfActivitiesOverlapping() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
