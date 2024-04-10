package example.cucumber;

import io.cucumber.java.en.Given;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.util.Calendar.*;

public class DateSteps {
 //Made by: Niklas
    MockDateHolder dateHolder;
    public DateSteps(MockDateHolder dateHolder) {this.dateHolder = dateHolder;}

    @Given("the date is {string},{string},{string}")
    public void theDateIs(String day, String month, String year) {
        Calendar date = new GregorianCalendar();
        date.set(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));

    }

    @Given("a year passes")
    public void aYearPasses() {
        dateHolder.advanceDateByDays(365);
    }
}
