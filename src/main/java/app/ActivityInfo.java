package app;

import domain.Classes.Activity;
import domain.Classes.User;

import java.util.*;

/*
    @author: Asger Allin Jensen
 */
public class ActivityInfo {
    private String activityName;
    private String parentProjectId;

    private Calendar startDate = null;
    private Calendar deadline = null;
    private double budgetTime;
    private boolean isComplete;

    private HashMap<String, Double> timeMap = new HashMap<String, Double>();

    private ArrayList<User> participantList = new ArrayList<User>();

    public ActivityInfo(){}

    public ActivityInfo(Activity activity){ // author Asger Allin Jensen
        this.activityName = activity.getName();
        this.startDate = activity.getStartdate();
        this.deadline = activity.getDeadline();
        this.budgetTime = activity.getBudgetTime();
        this.timeMap = activity.getTimeMap();
        this.participantList = activity.getParticipantList();
        this.isComplete = activity.getStatus();
        this.parentProjectId=activity.getParentProjectID();
    }

    //---- Get methods---------------------------------------------------------


    public String getStartDate() {    // Author: Niklas Emil Lysdal
        return dateToString(this.startDate);
    }
    public String getDeadline() { // Author: Nikolaj
        return dateToString(this.deadline);
        }
    public double getBudgetTime() {return budgetTime;} // Author: Nikolaj
    public boolean isComplete() {return isComplete;} // Author Lovro Antic
    public HashMap<String, Double> getTimeMap() { // Author: Lovro Antic
        return timeMap;}

    public ArrayList<String> getParticipantList() { // Asger Allin jensen
        return new ArrayList<>(this.participantList.stream().map(User::getUserId).toList());
    }
    public String getActivityName() {return activityName;} // Author: Nikolaj

    public String getParentProjectId() {return parentProjectId;} // Author: Lovro Antic
    public Calendar getStartdateCopy() { //Author: Niklas Emil Lysdal
        return (Calendar) this.startDate.clone();
    }
    public Calendar getDeadlineCopy(){ //Author: Niklas Emil Lysdal
        return (Calendar) this.deadline.clone();
    }
    //-----Miscellaneous --------------------------------------------------------------------------
    private static String dateToString(Calendar d) { //Author: Niklas Emil Lysdal
        if (d==null) {
            return "Date not Set";
        } else {
            return d.get(Calendar.DAY_OF_MONTH)+"/"+(d.get(Calendar.MONTH)+1)+"/"+d.get(Calendar.YEAR) +"(Week: "+d.get(Calendar.WEEK_OF_YEAR)+")";
        }
    }

    public String timeMapToString() { // Author Asger Allin Jensen
        String outputstring = "";
        if (this.getTimeMap().isEmpty()) {
            return outputstring;
        } else {
            String key;
            for(Iterator<String> var5 = this.getTimeMap().keySet().iterator(); var5.hasNext(); outputstring = outputstring + key + " : " + this.getTimeMap().get(key) + " Hours\n") {
                key = var5.next();
            }

            return outputstring;
        }
    }


}
