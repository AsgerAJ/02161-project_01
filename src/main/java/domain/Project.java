package domain;

import app.InvalidDateException;

import java.util.ArrayList;
import java.time.*;
import java.util.Calendar;
import java.util.Objects;
import app.InvalidDateException;
public class Project {
    private String name;
    private String projectID;
    private User projectLeader;
    private Calendar deadline;
    private Calendar startDate;
    private ArrayList<User> participanList = new ArrayList<User>();
    private ArrayList<Activity> activityList = new ArrayList<Activity>();


    public Project(String name,Calendar creationDate,int projectAmount){
        this.name = name;

        //generate project ID

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
                idExtension += projectAmount%10000; //modulo to reset when crossing limit
                break;

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
        this.activityList.add(new Activity(name, budgetTime));
    }

    public void createNewActivity(Activity activity){
        this.activityList.add(activity);
    }

    public ArrayList<Activity> getActivityList(){
        return this.activityList;
    }


    public String getTitle() {
        return this.name;
    }
    public String getProjectID() {
        return this.projectID;
    }

    public Activity getActivityFromName(String name){
        for (Activity activity : this.activityList) {
            if ((activity.getName().toLowerCase().equals(name.toLowerCase()))) {
                return activity;
            }
        }
        return null;
    }



    public ArrayList<User> getParticipantList() {
        return this.participanList;
    }

    public void setProjectLeader(User user){this.projectLeader = user;}

    public void setDeadline (Calendar date) throws InvalidDateException {

        if (this.startDate == null || date.after(this.startDate)) {
            this.deadline=date;
        } else {
            throw new InvalidDateException("deadline before start date");
        }
    }


    public Calendar getDeadline() {
        return this.deadline;
    }

    public boolean isOverdue(Calendar date) {

        if (this.deadline==null) {
            return false;
        }


        return date.after(this.deadline);

    }

    public void setStartDate(Calendar date) throws InvalidDateException {
        if (this.deadline ==null || date.before(this.deadline)) {
            this.startDate=date;
        } else {
            throw new InvalidDateException("Startdate after deadline");
        }
    }

    public Calendar getStartDate() {
        return this.startDate;
    }
}
