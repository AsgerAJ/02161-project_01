package domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Activity {
    private String name;
    private Calendar startDate;
    private Calendar deadline;
    private float budgetTime;
    private boolean isComplete = false;
    private HashMap<String, Float> timeMap = new HashMap<String, Float>();
    private float totalHours;
    private int weight;
    private ArrayList<User> participanList = new ArrayList<User>();


    public Activity(String name, float budgetTime){
        this.name = name;
        this.startDate = null;
        this.deadline = null;
        this.budgetTime = budgetTime;
    }

    public String getName(){return this.name;}
    public void setStatus(boolean status){
        this.isComplete = status;
    }

    public boolean isOverdue(){
        return false;
    }

    public void setStartDate(Calendar cal){this.startDate = cal;}

    public void setDeadline(Calendar cal){this.deadline = cal;}
}
