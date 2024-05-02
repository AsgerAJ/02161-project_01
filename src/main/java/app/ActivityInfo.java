package app;

import domain.Classes.Activity;
import domain.Classes.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ActivityInfo {
    private String activityName;
    private String parentProjectId;

    private Calendar startDate = null;
    private Calendar deadline = null;
    private double budgetTime;
    private boolean isComplete;
    private HashMap<String, Double> timeMap = new HashMap<String, Double>();
    private double totalHours = 0;
    private ArrayList<User> participantList = new ArrayList<User>();

    public ActivityInfo(){}

    public ActivityInfo(Activity activity){
        this.activityName = activity.getName();
        this.startDate = activity.getStartdate();
        this.deadline = activity.getDeadline();
        this.budgetTime = activity.getBudgetTime();
        this.timeMap = activity.getTimeMap();
        this.totalHours = activity.getTotalHours();
        this.participantList = activity.getParticipantList();
        this.isComplete = activity.getStatus();
    }

    public String getStartDate() {
        if (this.startDate==null) {
            return "Date not set.";
        } else {
            return ""+this.startDate.get(Calendar.DAY_OF_MONTH)+"/"+(startDate.get(Calendar.MONTH)+1)+"/"+startDate.get(Calendar.YEAR)+"(Week: "+startDate.get(Calendar.WEEK_OF_YEAR)+")";
        }
    }
    public String getDeadline() {
        if (this.deadline==null) {
            return "Date not set.";
        } else {
            return  ""+this.deadline.get(Calendar.DAY_OF_MONTH)+"/"+(deadline.get(Calendar.MONTH)+1)+"/"+deadline.get(Calendar.YEAR)+"(Week: "+deadline.get(Calendar.WEEK_OF_YEAR)+")";
        }
        }
    public double getBudgetTime() {return budgetTime;}
    public boolean getIsComplete() {return isComplete;}
    public HashMap<String, Double> getTimeMap() {return timeMap;}

    public String getParticipantList() {
        String result = "";
        for (User u : participantList) {
            result += u.getUserId() + " ";
        }
        return result;
    }
    public String getActivityName() {return activityName;}

    public String getParentProjectId() {return parentProjectId;}


    public void setParentProjectID(String projectID) {
        this.parentProjectId = projectID;
    }
}
