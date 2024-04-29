package app;

import domain.Activity;
import domain.Project;
import domain.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ActivityInfo {
    private String activityName;
    private String parentProjectId;

    private Calendar startDate = null;
    private Calendar deadline = null;
    private double budgetTime;
    private boolean isComplete = false;
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
    }

    public Calendar getStartDate() {return startDate;}
    public Calendar getDeadline() {return deadline;}
    public double getBudgetTime() {return budgetTime;}
    public boolean getIsComplete() {return isComplete;}
    public HashMap<String, Double> getTimeMap() {return timeMap;}
    public double getTotalHours() {return totalHours;}
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
