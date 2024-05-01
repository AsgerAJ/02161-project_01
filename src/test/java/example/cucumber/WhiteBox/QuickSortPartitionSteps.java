package example.cucumber.WhiteBox;

import domain.DataPackage;
import domain.UserCount;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuickSortPartitionSteps {
    private QuickSortUserCounts sorter;
    private boolean exceptionThrown=false;

    public QuickSortPartitionSteps(QuickSortUserCounts q) {this.sorter=q;}



    private int beginIndex;
    private int endIndex;
    private ArrayList<UserCount> list = new ArrayList<>();
    private int result;

    @Given("the beginning index is {int}")
    public void theBeginningIndexIs(Integer int1) {
        this.beginIndex=int1;
    }
    @Given("the end index is {int}")
    public void theEndIndexIs(Integer int1) {
        this.endIndex=int1;
    }
    @Given("the arraylist contains a dataPackage with a null user, and a count of {int}")
    public void theArraylistContainsADataPackageWithANullUserAndACountOf(Integer int1) {
       UserCount d = new DataPackage();
       d.setUser(null);
       d.setCount(int1);
       list.add(d);
    }
    @When("partition is called")
    public void partitionIsCalled() {
            result=sorter.partition(this.list,beginIndex,endIndex);
    }
    @Then("{int} is returned")
    public void isReturned(Integer int1) {
        assertEquals(this.result, (int) int1);
    }
    @Then("the dataPackage at index {int} has count {int}")
    public void theDataPackageAtIndexHasCount(Integer int1, Integer int2) {
        assertTrue(this.list.get(int1).getCount()==int2);
    }


    @Then("an exception is thrown")
    public void anExceptionIsThrown() {
        assertTrue(this.exceptionThrown);
    }



}
