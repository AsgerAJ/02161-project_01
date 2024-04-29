package app;

import domain.Activity;
import domain.Project;
import domain.User;

import java.util.ArrayList;
import java.util.Calendar;

public class ProjectInfo {

    private String name;
    private String projectID;
    private User projectLeader;
    private Calendar deadline;
    private Calendar startDate;
    private ArrayList<User> participanList = new ArrayList<User>();
    private ArrayList<Activity> activityList = new ArrayList<Activity>();

    public ProjectInfo(){}

    public ProjectInfo(Project project){
        this.name = project.getTitle();
        this.projectID = project.getProjectID();
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

    public Calendar getDeadline() {return deadline;}

    public Calendar getStartDate() {return startDate;}

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
}
