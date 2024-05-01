package example.cucumber.steps;

import app.App;
import domain.Activity;
import domain.User;
import domain.UserCount;
import example.cucumber.helpers.ErrorMessageHolder;
import example.cucumber.helpers.MockDateHolder;
import example.cucumber.helpers.ProjectHelper;
import example.cucumber.helpers.UserHelper;
import example.cucumber.helpers.ActivityHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

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
            for (int userIndex=0; userIndex<=actIndex+1; userIndex++) {
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
        ArrayList<User> userList = this.userHelper.getExampleUsersList();
        ArrayList<User> returnedUsers = new ArrayList<>();
        ArrayList<UserCount> results = this.userHelper.getAvailableUserList();
        for (UserCount uc : results) {
            returnedUsers.add(uc.getUser());

        }
        assertTrue(returnedUsers.containsAll(userList));
    }

    @Then("the returned users are sorted by amount of activities overlapping")
    public void theReturnedUsersAreSortedByAmountOfActivitiesOverlapping() {
        ArrayList<UserCount> results = this.userHelper.getAvailableUserList();

        int[] counts =  new int[results.size()];
        for (int i = 0; i<results.size(); i++) {
            counts[i] = results.get(i).getCount();
        }
        for (int i = 1; i<counts.length;i++) {
            assertTrue(counts[i]>=counts[i-1]);
        }
    }

    @Then("no users are found")
    public void noUsersFound(){
        ArrayList<UserCount> results = this.userHelper.getAvailableUserList();
        assertTrue(results.size()==0);
    }

    @Then("{string} is not found")
    public void userIsNotFound(String user){
        ArrayList<UserCount> results = this.userHelper.getAvailableUserList();
        for (UserCount userCount : results) {
            if (userCount.getUser().getUserId().equals(user)) {
                assertTrue(false);
            }
            else{
                assertTrue(true);
            }
        }

    }
}
