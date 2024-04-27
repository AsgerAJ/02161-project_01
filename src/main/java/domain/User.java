package domain;

import java.util.ArrayList;

public class User {
    private String userId;
    private ArrayList<Project> assignedProjects = new ArrayList<Project>();
    private ArrayList<PeriodEvent> assignedActivities = new ArrayList<PeriodEvent>();

    public User(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return this.userId;
    }

    public void assignProject(Project project){
        this.assignedProjects.add(project);
    }

    public void assignActivity(PeriodEvent activity){
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

    public ArrayList<Project> getAssignedProjects() {
        return assignedProjects;
    }

    public SuccessAmount isAvailable(PeriodEvent event) {
        SuccessAmount result = new DataPackage();


        result.setTruthValue(assignedActivities.stream().filter(p->p.timeOverlap(event)).noneMatch(p-> p.timeLockdown()));
        if (result.isTrue()) {
            result.setAmount((int) (assignedActivities.stream().filter(p->p.timeOverlap(event)&&!p.timeLockdown()).count()));
        }
        return result;

        //for (PeriodEvent p : assignedActivities) {
        //    if (p.timeOverlap(event)) {
        //        if (p.timeLockdown()) {
        //            result.setTruthValue(false);
        //            break;
        //        }
//
//
        //    }
        //}
        //return result;


    }


}
