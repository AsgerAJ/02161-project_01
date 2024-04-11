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


    public Project(String name,Calendar creationDate,int projectAmount){
        this.name = name;

        //generate project ID

        String idExtension = "";
        if (projectAmount >= 1000) {
            idExtension += projectAmount;
        } else if (projectAmount >= 100) {
            idExtension += "0" + projectAmount;
        } else if (projectAmount >= 10) {
            idExtension += "00" + projectAmount;
        } else {
            idExtension += "000" + projectAmount;
        }
        this.projectID = String.valueOf(creationDate.get(Calendar.YEAR)).substring(2,4)+idExtension;
    }

    public void assignUser(User user){
        this.participanList.add(user);
        user.assignProject(this);
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
    public String getTitle() {
        return this.name;
    }
    public String getProjectID() {
        return this.projectID;
    }

    public ArrayList<User> getParticipanList() {
        return this.participanList;
    }
}
