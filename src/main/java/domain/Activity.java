package domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Activity {
    private String name;
    private Calendar startDate = null;
    private Calendar deadline = null;
    private float budgetTime;
    private boolean isComplete = false;
    private HashMap<String, Float> timeMap = new HashMap<String, Float>();
    private float totalHours = 0;
    private int weight;
    private ArrayList<User> participanList = new ArrayList<User>();


    public Activity(String name, float budgetTime){
        this.name = name;
        this.budgetTime = budgetTime;
    }

    public String getName(){return this.name;}
    public void setStatus(boolean status){
        this.isComplete = status;
    }

    public boolean getStatus(){return this.isComplete;}

    public boolean isOverdue(Calendar today){
        return today.after(this.deadline);
    }

    public boolean isOverBudget(){return this.totalHours > this.budgetTime;}

    public void setStartDate(Calendar cal){this.startDate = cal;}

    public void setDeadline(Calendar cal){this.deadline = cal;}

    public Calendar getStartDate(){
        return this.startDate;
    }
    public Calendar getDeadLine(){
        return this.deadline;
    }

    public ArrayList<User> getParticipanList() {return this.participanList;}

    public void logTime(float workedTime, User user){
        String userId = user.getUserId();
        timeMap.put(userId, timeMap.get(userId) + workedTime);
        this.totalHours += workedTime;
    }

    public float getTotalHours(){
        return this.totalHours;
    }



}
