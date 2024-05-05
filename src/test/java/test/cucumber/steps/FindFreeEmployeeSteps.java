package test.cucumber.steps;

import app.App;
import domain.Classes.Activity;
import domain.Classes.User;
import domain.Interfaces.FinalUserCount;
import domain.exceptions.UserIdDoesNotExistExeption;
import test.cucumber.helpers.UserHelper;
import test.cucumber.helpers.ActivityHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class FindFreeEmployeeSteps {

    private App app;
    private UserHelper userHelper;
    private ActivityHelper activityHelper;


    public FindFreeEmployeeSteps(App app, UserHelper userHelper, ActivityHelper a){
        this.app = app;
        this.userHelper=userHelper;
        this.activityHelper=a;
    }

    @Given("the users have different amounts of activities")
    public void theUsersHaveDifferentAmountsOfActivities() throws UserIdDoesNotExistExeption {
        ArrayList<Activity> actList = this.activityHelper.getExampleActivityList();
        ArrayList<User> userList=this.userHelper.getExampleUsersList();

        for (int actIndex = 0; actIndex<actList.size(); actIndex++) { //activity 1 to 1 users, activity 2 to 2 users...
            for (int userIndex=0; userIndex<=actIndex+1; userIndex++) {
                if (userIndex<userList.size()) {

                    app.assignUserToActivity(userList.get(userIndex).asInfo().getUserId(),actList.get(actIndex).asInfo());

                }
            }
        }
    }

    @When("the user searches for free employee")
    public void theUserSearchesForFreeEmployee() {
        this.userHelper.setAvailableUserFinalList(app.findFreeEmployee(this.activityHelper.getActivityInfo()));
    }
    @Then("all users are returned")
    public void allUsersAreReturned() {
        ArrayList<String> userList = new ArrayList<>(this.userHelper.getExampleUsersList().stream().map(u->u.getUserId()).toList());

        ArrayList<String> returnedUsers = new ArrayList<>();
        ArrayList<FinalUserCount> results = this.userHelper.getAvailableUserFinalList();
        for (FinalUserCount uc : results) {
            returnedUsers.add(uc.getUserID());

        }
        assertTrue(returnedUsers.containsAll(userList));
    }

    @Then("the returned users are sorted by amount of activities overlapping")
    public void theReturnedUsersAreSortedByAmountOfActivitiesOverlapping() {
        ArrayList<FinalUserCount> results = this.userHelper.getAvailableUserFinalList();
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
        assertTrue(this.userHelper.getAvailableUserFinalList().isEmpty());
    }

    @Then("{string} is not found")
    public void userIsNotFound(String user){
        assertTrue(this.userHelper.getAvailableUserFinalList().stream().noneMatch(u->u.getUserID().equalsIgnoreCase(user)));
    }
}
