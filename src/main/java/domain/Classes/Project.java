package domain.Classes;

import app.ProjectInfo;
import domain.Interfaces.SuccessCount;
import domain.Interfaces.UserCount;
import domain.exceptions.InvalidDateException;

import java.util.ArrayList;
import java.util.Calendar;

public class Project {
    private final String name;
    private final String projectID;
    private User projectLeader;
    private Calendar deadline;
    private Calendar startDate;

    private boolean complete = false;
    private final ArrayList<User> participanList = new ArrayList<User>();
    private final ArrayList<Activity> activityList = new ArrayList<Activity>();

    public Project(String name, Calendar creationDate, int projectAmount) {
        this.name = name;
        this.complete = false;
        // generate project ID

        String year = String.valueOf(creationDate.get(Calendar.YEAR)).substring(2, 4);
        this.projectID = formatProjectId(year, projectAmount);
    }
    //------- Get methods --------------------------------------------------------------
    public String getName() {return this.name;}

    public String getProjectID() {return this.projectID;}

    public ArrayList<Activity> getActivityList() {return this.activityList;}

    public ArrayList<User> getParticipantList() {return this.participanList;}

    public Calendar getStartDate() {return this.startDate;}

    public Calendar getDeadline() {return this.deadline;}



    public Activity getActivityFromName(String name) {
        return this.activityList.stream().filter(a->a.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    public String getID() {
        return this.projectID;
    }

    public User getProjectLeader() {
        return this.projectLeader;
    }

    public boolean getStatus() {
        return this.complete;
    }

    //-----Set methods ----------------------------------------------------------------------------


    public void setProjectLeader(User user) {
        this.projectLeader = user;
    }

    public void setDeadline(Calendar date) throws InvalidDateException {

        if (this.startDate == null || date.after(this.startDate)) {
            this.deadline = date;
        } else {
            throw new InvalidDateException("deadline before start date");
        }
    }
    public void setStartDate(Calendar date) throws InvalidDateException {
        if (this.deadline == null || date.before(this.deadline)) {
            this.startDate = date;
        } else {
            throw new InvalidDateException("Startdate after deadline");
        }
    }

    //------Functional----------------------------------------------------------------------------------

    public String formatProjectId(String year, int projectAmount) {
        String idExtension = "";

        switch (String.valueOf(projectAmount).length()) {
            case 1:
                idExtension += "000" + projectAmount;
                break;
            case 2:
                idExtension += "00" + projectAmount;
                break;
            case 3:
                idExtension += "0" + projectAmount;
                break;
            default:
                idExtension += projectAmount % 10000; // modulo to reset when crossing limit
                break;
        }
        
        return year + idExtension;
    }

    public void assignUser(User user) {
        if (!this.participanList.contains(user)) {
            this.participanList.add(user);
            user.assignProject(this);
        }
    }
    public void removeUser(User user) {
        if (this.participanList.contains(user)) {
            this.participanList.remove(user);
            user.removeProject(this);
        }
    }
    public void changeCompleteness(boolean status){this.complete = status;}

    public void createNewActivity(Activity activity) {
        this.activityList.add(activity);
    }
    
    //Open-closed principle
    public ArrayList<UserCount> findFreeEmployee(PeriodEvent activity) {
        if (activity.getStartdate() == null || activity.getDeadline() == null) {
            return new ArrayList<UserCount>();
        }else{
            ArrayList<UserCount> returnList = new ArrayList<>();
            for (User user : this.participanList) {
                SuccessCount result = user.isAvailable(activity);

                if (result.isTrue()) {
                    UserCount temp =(UserCount) result;
                    temp.setUser(user);
                    returnList.add(temp);
                }

            }
            returnList.sort(new CountSorting());

            return returnList;
        }


    }

    //----------Checks -------------------------------------------------------------------------------



    public boolean isProjectLeader(User user) {
        return this.projectLeader == user;
    }






    public boolean isOverdue(Calendar date) {

        if (this.deadline == null) {
            return false;
        }

        return date.after(this.deadline);

    }

    //-----As info---------------------------------------------------------------------------
    public ProjectInfo asInfo() {
        return new ProjectInfo(this);
    }

}
