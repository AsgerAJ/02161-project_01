package domain.Classes;

import app.ActivityInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Activity extends PeriodEvent {
    private double budgetTime;
    private boolean isComplete = false;
    private final HashMap<String, Double> timeMap = new HashMap<String, Double>();
    private double totalHours = 0;
    private final ArrayList<User> participantList = new ArrayList<User>();
    private final String parentProjectID;

    public Activity(String name, double budgetTime, String parentProjectID) {
        super(name);
        this.budgetTime = budgetTime;
        this.parentProjectID=parentProjectID;
    }
    //------Get Methods ----------------------------------------------------------------------------
    public void setStatus(boolean status) {
        this.isComplete = status;
    }

    public boolean getStatus() {return this.isComplete;}

    public HashMap<String, Double> getTimeMap() {return this.timeMap;}



    public double getBudgetTime() {return this.budgetTime;}

    public ArrayList<User> getParticipantList() {return this.participantList;}
    public String getParentProjectID(){return this.parentProjectID;}

    //-----Checks ------------------------------------------------------------------------------
    public boolean isOverdue(Calendar today) {
        return !isComplete && today.after(this.getDeadline()); //not overdue if complete
    }

    public boolean isOverBudget() {
        return this.totalHours > this.budgetTime;
    }


    //------ Functional--------------------------------------------------------------------------

    public void logTime(double workedTime, User user) {
        String userId = user.getUserId();
        if (timeMap.containsKey(userId)) {
            timeMap.put(userId, timeMap.get(userId) + workedTime);
        } else {
            timeMap.put(userId, workedTime);
        }

        this.totalHours += workedTime;
    }

    public void assignUser(User u) {
        this.participantList.add(u);
        u.assignActivity(this);
    }

    public void removeUser(User u) {
        this.participantList.remove(u);
        u.removeActivity(this);
    }
    //----As info-----------------------------------------------------------------------------------
    public ActivityInfo asInfo() {
        return new ActivityInfo(this);
    }



}
