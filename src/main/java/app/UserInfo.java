package app;

import domain.Classes.PeriodEvent;
import domain.Classes.Project;
import domain.Classes.User;

import java.util.ArrayList;

public class UserInfo {
    private String userId;
    private ArrayList<Project> assignedProjects = new ArrayList<Project>();
    private ArrayList<PeriodEvent> assignedActivities = new ArrayList<PeriodEvent>();


    public UserInfo(User user){
        this.userId = user.getUserId();
        this.assignedProjects = user.getAssignedProjects();
        this.assignedActivities = user.getAssignedActivities();

    }

    public UserInfo(){}

    public String getUserId(){
        return this.userId;
    }

    public ArrayList<Project> getAssignedProjects() {
        return assignedProjects;
    }

    public ArrayList<PeriodEvent> getAssignedActivities() {
        return assignedActivities;
    }
}
