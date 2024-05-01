package example.cucumber.steps;

import app.App;
import domain.Classes.Leave;
import domain.Classes.PeriodEvent;
import domain.exceptions.InvalidDateException;
import domain.exceptions.UserIdDoesNotExistExeption;
import example.cucumber.helpers.ProjectHelper;
import example.cucumber.helpers.UserHelper;
import example.cucumber.helpers.ErrorMessageHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LeaveSteps {
    private App app;
    private ErrorMessageHolder errorMessage;
    private UserHelper userHelper;
    private ProjectHelper projecthelper;
    public LeaveSteps(App app, ErrorMessageHolder errorMessage,UserHelper userHelper,ProjectHelper p){
        this.app = app;
        this.errorMessage = errorMessage;
        this.userHelper = userHelper;
        this.projecthelper=p;
    }
    
    @Given("user {string} is on {string} leave from {int},{int},{int} to {int},{int},{int}")
    public void userIsOnLeave(String user, String leave_name, int start_day, int start_month, int start_year, int end_day, int end_month, int end_year) throws InvalidDateException {
        Calendar startdate = new GregorianCalendar(start_year, start_month-1, start_day);
        Calendar deadline = new GregorianCalendar(end_year, end_month-1, end_day);
        Leave leave = new Leave(leave_name, startdate, deadline);
        try {
            this.app.getUserFromId(user).assignActivity(leave);
        } catch (UserIdDoesNotExistExeption e) {
            errorMessage.setErrorMessage(e.getMessage());
        }        
    }

    @When("user {string} goes on {string} leave from {int},{int},{int} to {int},{int},{int}")
    public void userGoesOnLeave(String user, String leave_name, int start_day, int start_month, int start_year, int end_day, int end_month, int end_year) throws InvalidDateException {
        Calendar startdate = new GregorianCalendar(start_year, start_month-1, start_day);
        Calendar deadline = new GregorianCalendar(end_year, end_month-1, end_day);
        Leave leave = new Leave(leave_name, startdate, deadline);
        try {
            this.app.getUserFromId(user).assignActivity(leave);
        } catch (UserIdDoesNotExistExeption e) {
            errorMessage.setErrorMessage(e.getMessage());
        }        
    }

    @Then("the user has the {string} even assigned to them")
    public void userAssignedLeave(String leave){
        ArrayList<PeriodEvent> eventList = this.userHelper.getUser().getAssignedActivities();
        boolean check = false;
        for (PeriodEvent event: eventList) {
            if (event.getName().equals(leave)) {
                check = true;
            }
        }
        assertTrue(check);
    }
}
