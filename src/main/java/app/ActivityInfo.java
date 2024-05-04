package app;

import domain.Classes.Activity;
import domain.Classes.User;

import java.util.*;

public class ActivityInfo {
    private String activityName;
    private String parentProjectId;

    private Calendar startDate = null;
    private Calendar deadline = null;
    private double budgetTime;
    private boolean isComplete;
    private boolean isOverdue;
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
        this.parentProjectId=activity.getParentProjectID();
    }

    //---- Get methods---------------------------------------------------------
    public String getStartDate() {
        return dateToString(this.startDate);
    }
    public String getDeadline() {
        return dateToString(this.deadline);
        }
    public double getBudgetTime() {return budgetTime;}
    public boolean isComplete() {return isComplete;}
    public HashMap<String, Double> getTimeMap() {
        return timeMap;}

    public ArrayList<String> getParticipantList() {
        return new ArrayList<>(this.participantList.stream().map(User::getUserId).toList());
    }
    public String getActivityName() {return activityName;}

    public String getParentProjectId() {return parentProjectId;}
    public Calendar getStartdateCopy() {
        return (Calendar) this.startDate.clone();
    }
    public Calendar getDeadlineCopy(){
        return (Calendar) this.deadline.clone();
    }
    //-----Miscellaneous --------------------------------------------------------------------------
    private static String dateToString(Calendar d) {
        if (d==null) {
            return "Date not Set";
        } else {
            return d.get(Calendar.DAY_OF_MONTH)+"/"+(d.get(Calendar.MONTH)+1)+"/"+d.get(Calendar.YEAR) +"(Week: "+d.get(Calendar.WEEK_OF_YEAR)+")";
        }
    }

    public String timeMapToString() {
        String outputstring = "";
        if (this.getTimeMap().isEmpty()) {
            return outputstring;
        } else {
            String key;
            for(Iterator<String> var5 = this.getTimeMap().keySet().iterator(); var5.hasNext(); outputstring = outputstring + key + " : " + this.getTimeMap().get(key) + " Hours\n") {
                key = (String)var5.next();
            }

            return outputstring;
        }
    }


}
