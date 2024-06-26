package test.cucumber.steps;

import test.cucumber.helpers.MockDateHolder;
import io.cucumber.java.en.Given;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateSteps {
 //Made by: Niklas
    MockDateHolder dateHolder;
    public DateSteps(MockDateHolder dateHolder) {this.dateHolder = dateHolder;}

    @Given("the date is {int},{int},{int}")
    public void theDateIs(Integer day, Integer month, Integer year) {
        Calendar date = new GregorianCalendar();
        date.set(year,month-1,day); //january is month 0
        dateHolder.setDate(date);
    }
    @Given("{int} year\\(s) pass\\(es)")
    public void yearSPassEs(Integer years) {
        dateHolder.advanceDateByDays(years*365);
    }

}
