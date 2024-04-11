package domain;

import java.util.ArrayList;

public class User {
    private String userId;
    private ArrayList<Project> assignedProjects = new ArrayList<Project>();
    private ArrayList<Activity> assignedActivities = new ArrayList<Activity>();

    public User(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return this.userId;
    }

    public void assignProject(Project project){
        this.assignedProjects.add(project);
    }

    public void assignActivity(Activity activity){
        this.assignedActivities.add(activity);
    }

    public int isAvailable(Activity activity){
        return 0;
    }

    public boolean hasProjectWithID(String projectID) {
        for (Project p : assignedProjects) {
            if (p.getProjectID().equals(projectID)) {
                return true;
            }
        }
        return false;
    }
}
