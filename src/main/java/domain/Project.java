package domain;

import java.util.ArrayList;
import java.time.*;
import java.util.Calendar;

public class Project {
    private String name;
    private String projectID;
    private User projectLeader;
    private ArrayList<User> participanList = new ArrayList<User>();
    private ArrayList<Activity> activityList = new ArrayList<Activity>();


    public Project(String name){
        this.name = name;
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);
        this.projectID = yearInString.substring(3,5);
    }

    public void assignUser(User user){
        this.participanList.add(user);
    }

    public boolean hasProjectLeader(){
        return this.projectLeader != null;
    }

    public boolean isProjectLeader(User user){
        return this.projectLeader == user;
    }

    public void completeActivity(Activity activity){
        activity.setStatus(true);
    }

    public void createNewActivity(String name, Date startDate, Date deadline, float budgetTime){
        this.activityList.add(new Activity(name, startDate, deadline, budgetTime));
    }

    
}
