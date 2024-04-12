package domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Activity {
    private String name;
    private Date startDate;
    private Date deadline;
    private float budgetTime;
    private boolean isComplete = false;
    private HashMap<String, Float> timeMap = new HashMap<String, Float>();
    private float totalHours;
    private int weight;
    private ArrayList<User> participanList = new ArrayList<User>();


    public Activity(String name, Date startDate, Date deadline, float budgetTime){
        this.name = name;
        this.startDate = startDate;
        this.deadline = deadline;
        this.budgetTime = budgetTime;
    }

    public String getName(){return this.name;}
    public void setStatus(boolean status){
        this.isComplete = status;
    }


    public boolean isOverdue(){
        return false;
    }


}
