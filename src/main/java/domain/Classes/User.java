package domain.Classes;

import app.UserInfo;
import domain.Interfaces.SuccessCount;
import domain.exceptions.InvalidDateException;

import java.util.ArrayList;
import java.util.Calendar;

public class User {
    private final String userId;
    private final ArrayList<Project> assignedProjects = new ArrayList<Project>();
    private final ArrayList<PeriodEvent> assignedActivities = new ArrayList<PeriodEvent>();

    public User(String userId){
        this.userId = userId;
    }
    //---- Get methods ------------------------------------------------------------------------
    public String getUserId(){
        return this.userId;
    }

    public ArrayList<Project> getAssignedProjects() {
        return assignedProjects;
    }

    //---- Functional -----------------------------------------------------------------------------

    public void assignProject(Project project){
        this.assignedProjects.add(project);
    }
    public void removeProject(Project project){
        this.assignedProjects.remove(project);
    }

    public void assignActivity(PeriodEvent activity){
        this.assignedActivities.add(activity);
    }

    public void registerLeave(String name, Calendar start, Calendar end){
        try {
            Leave leave = new Leave(name, start, end);
            this.assignedActivities.add(leave);
        } catch (InvalidDateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeLeave(String name, Calendar start, Calendar end){
        PeriodEvent leave = this.assignedActivities.stream().filter(p->p instanceof Leave && p.getName().equalsIgnoreCase(name) && p.getStartdate().equals(start) && p.getDeadline().equals(end)).findFirst().orElse(null);
        this.assignedActivities.remove(leave);
    }

    public void removeActivity(PeriodEvent activity){
        this.assignedActivities.remove(activity);
    }
    public ArrayList<PeriodEvent> getAssignedActivities() {
        return assignedActivities;
    }
    //--------Checks----------------------------------------------------------------------------
    public boolean hasProjectWithID(String projectID) {
        for (Project p : assignedProjects) {
            if (p.getProjectID().equals(projectID)) {
                return true;
            }
        }
        return false;
    }




    //Open-closed principle here, of the SOLID principles
    public SuccessCount isAvailable(PeriodEvent event) {
        if (this.assignedActivities.contains(event)) {
            SuccessCount d= new DataPackage();
            d.setTruthValue(false);
            return d;
        }
        SuccessCount result = new DataPackage();
        result.setTruthValue(true);

        for (PeriodEvent p : this.assignedActivities) {
            if (p.getStartdate() == null || p.getDeadline() == null){
                continue;
            } 

            if (p.timeOverlap(event)) {
                if (p.timeLockdown()) {
                    result.setTruthValue(false);
                } else {
                    result.increaseCount(1);
                }
            }
        }
        return result;
    }


    //------ As Info----------------------------------------------------------------------------
    public UserInfo asInfo() {
        return new UserInfo(this);
    }

}
