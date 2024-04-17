package domain;

import java.util.ArrayList;
import java.time.*;
import java.util.Calendar;
import java.util.Objects;

public class Project {
    private String name;
    private String projectID;
    private User projectLeader;
    private Calendar deadline;
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

    public void setDeadline (Calendar date) {
        this.deadline=date;
    }
    public Calendar getDeadline() {
        return this.deadline;
    }

    public boolean isOverdue(Calendar date) {
        System.out.println("date_year: " + date.get(Calendar.YEAR));
        System.out.println("date_month:" + date.get(Calendar.MONTH));
        System.out.println("deadline: "+this.deadline.get(Calendar.YEAR));
        if (this.deadline==null) {
            return false;
        }
        if (date==null) {
            throw new NullPointerException("date is null");
        }

        return date.after(this.deadline);

    }

    public String completionPercentage(){
        double completionPercentage = 0;
        for(int i = 0; i < this.getActivityList().size(); i++){
            if(this.getActivityList().get(i).getStatus()){
                completionPercentage += 100.0/this.activityList.size();
            }
        }
        completionPercentage = completionPercentage/100;
        int totalBlocks = 20;
        int completedBlocks = (int) (completionPercentage * totalBlocks);

        StringBuilder progressBarBuilder = new StringBuilder();
        for (int i = 0; i < totalBlocks; i++) {
            if (i < completedBlocks) {
                progressBarBuilder.append("■"); // Filled block
            } else {
                progressBarBuilder.append("□"); // Empty block
            }
        }

        return progressBarBuilder.toString();
    }
}
