package test.cucumber.steps;

import app.App;
import domain.Classes.PeriodEvent;
import domain.exceptions.InvalidDateException;
import domain.exceptions.UserIdDoesNotExistExeption;
import test.cucumber.helpers.ProjectHelper;
import test.cucumber.helpers.UserHelper;
import test.cucumber.helpers.ErrorMessageHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LeaveSteps {
    private App app;
    private ErrorMessageHolder errorMessage;
    private UserHelper userHelper;
    public LeaveSteps(App app, ErrorMessageHolder errorMessage,UserHelper userHelper,ProjectHelper p){
        this.app = app;
        this.errorMessage = errorMessage;
        this.userHelper = userHelper;
    }
    
    @Given("user {string} is on {string} leave from {int},{int},{int} to {int},{int},{int}")
    public void userIsOnLeave(String user, String leave_name, int start_day, int start_month, int start_year, int end_day, int end_month, int end_year) throws InvalidDateException {
        Calendar startdate = new GregorianCalendar(start_year, start_month-1, start_day);
        Calendar deadline = new GregorianCalendar(end_year, end_month-1, end_day);
        try {
            this.app.getUserFromId(user).registerLeave(leave_name, startdate, deadline);
        } catch (UserIdDoesNotExistExeption e) {
            errorMessage.setErrorMessage(e.getMessage());
        }        
    }

    @When("user {string} goes on {string} leave from {int},{int},{int} to {int},{int},{int}")
    public void userRemovesLeave(String user, String leave_name, int start_day, int start_month, int start_year, int end_day, int end_month, int end_year) throws InvalidDateException {
        Calendar startdate = new GregorianCalendar(start_year, start_month-1, start_day);
        Calendar deadline = new GregorianCalendar(end_year, end_month-1, end_day);
        try {
            this.app.getUserFromId(user).registerLeave(leave_name, startdate, deadline);;
        } catch (UserIdDoesNotExistExeption e) {
            errorMessage.setErrorMessage(e.getMessage());
        }        
    }

    @When("user {string} removes {string} leave from {int},{int},{int} to {int},{int},{int}")
    public void userGoesOnLeave(String user, String leave_name, int start_day, int start_month, int start_year, int end_day, int end_month, int end_year) throws InvalidDateException {
        Calendar startdate = new GregorianCalendar(start_year, start_month-1, start_day);
        Calendar deadline = new GregorianCalendar(end_year, end_month-1, end_day);
        try {
            this.app.getUserFromId(user).removeLeave(leave_name, startdate, deadline);
        } catch (UserIdDoesNotExistExeption e) {
            errorMessage.setErrorMessage(e.getMessage());
        }        
    }

    @Then("the user has the {string} event assigned to them")
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

    @Then("the user does not have the {string} event assigned to them")
    public void userNotAssignedLeave(String leave){
        ArrayList<PeriodEvent> eventList = this.userHelper.getUser().getAssignedActivities();
        boolean check = false;
        for (PeriodEvent event: eventList) {
            if (event.getName().equals(leave)) {
                check = true;
            }
        }
        assertFalse(check);
    }
}
