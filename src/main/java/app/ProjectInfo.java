package app;

import domain.Classes.Activity;
import domain.Classes.Project;
import domain.Classes.User;

import java.util.ArrayList;
import java.util.Calendar;

public class ProjectInfo {

    private String name;
    private String projectID;
    private User projectLeader;
    private Calendar deadline;
    private Calendar startDate;

    private boolean complete = false;
    private ArrayList<User> participanList = new ArrayList<User>();
    private ArrayList<Activity> activityList = new ArrayList<Activity>();

    public ProjectInfo(){}

    public ProjectInfo(Project project){
        this.name = project.getName();
        this.projectID = project.getProjectID();
        this.complete = project.getStatus();
        this.projectLeader = project.getProjectLeader();
        this.deadline = project.getDeadline();
        this.startDate = project.getStartDate();
        this.participanList = project.getParticipantList();
        this.activityList = project.getActivityList();
    }

    public String getProjectID() {
        return projectID;
    }

    public String getName() {return this.name;}

    public String getProjectLeader() {return projectLeader.getUserId();}

    public String getDeadline() {
        if (this.deadline == null) {
            return "Date not set";
        } else {
            return ""+this.deadline.get(Calendar.DAY_OF_MONTH)+"/"+(deadline.get(Calendar.MONTH)+1)+"/"+deadline.get(Calendar.YEAR)+"(Week: "+deadline.get(Calendar.WEEK_OF_YEAR)+")";
        }
    }

    public String getStartDate() {
        if (startDate==null) {
            return "Date not set";
        } else {
            return ""+this.startDate.get(Calendar.DAY_OF_MONTH)+"/"+(startDate.get(Calendar.MONTH)+1)+"/"+startDate.get(Calendar.YEAR)+"(Week: "+startDate.get(Calendar.WEEK_OF_YEAR)+")";
        }

    }

    public String getParticipantList() {
        String result = "";
        for (User u : participanList) {
            result += u.getUserId() + " ";
        }
        return result;
    }

    public ArrayList<Activity> getActivityList() {
        return activityList;
    }

    public String getParticipanList() {
        String result = "";
        for (User u : participanList) {
            result += u.getUserId() + " ";
        }
        return result;
    }

    public boolean getComplete() {
        return complete;
    }

}
