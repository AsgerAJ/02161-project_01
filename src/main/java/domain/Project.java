package domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class Project {
    private String name;
    private String projectID;
    private User projectLeader;
    private Calendar deadline;
    private Calendar startDate;

    private boolean complete = false;
    private ArrayList<User> participanList = new ArrayList<User>();
    private ArrayList<Activity> activityList = new ArrayList<Activity>();

    public Project(String name, Calendar creationDate, int projectAmount) {
        this.name = name;
        this.complete = false;
        // generate project ID

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

        this.projectID = String.valueOf(creationDate.get(Calendar.YEAR)).substring(2, 4) + idExtension;
    }

    public String getName() {return this.name;}

    public String getProjectID() {return this.projectID;}

    public ArrayList<Activity> getActivityList() {return this.activityList;}

    public ArrayList<User> getParticipantList() {return this.participanList;}

    public Calendar getStartDate() {return this.startDate;}

    public Calendar getDeadline() {return this.deadline;}



    public Activity getActivityFromName(String name) {
        for (Activity activity : this.activityList) {
            if ((activity.getName().toLowerCase().equals(name.toLowerCase()))) {
                return activity;
            }
        }
        return null;
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

    public boolean hasProjectLeader() {
        return this.projectLeader != null;
    }

    public boolean isProjectLeader(User user) {
        return this.projectLeader == user;
    }

    public void completeProject(boolean status){this.complete = status;}

    public void createNewActivity(Activity activity) {
        this.activityList.add(activity);
    }


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

    public boolean isOverdue(Calendar date) {

        if (this.deadline == null) {
            return false;
        }

        return date.after(this.deadline);

    }

    public String completionPercentage(){
        if(this.getActivityList() != null){
            double completionPercentage = 0;

            for(int i = 0; i < this.getActivityList().size(); i++){
                if(this.getActivityList().get(i).getStatus()){
                    completionPercentage += 100.0/this.activityList.size();
                }
            }

            completionPercentage = completionPercentage/100;
            int totalBlocks = 20;
            completeProject(completionPercentage == 1);
            int completedBlocks = (int) (completionPercentage * totalBlocks);

            StringBuilder progressBarBuilder = new StringBuilder();
            for (int i = 0; i < totalBlocks; i++) {
                if (i < completedBlocks) {
                    progressBarBuilder.append("■"); // Filled block
                } else {
                    progressBarBuilder.append("□"); // Empty block
                }
            }

            return "Project " + progressBarBuilder.toString() + " " +(completionPercentage * 100) + "% complete";
        }else{
            return "Project □□□□□□□□□□□□□□□□□□□□ 0% complete";
        }

    }



    public ArrayList<UserCount> findFreeEmployee(PeriodEvent activity) {
        if (activity.getStartdate() == null || activity.getDeadline() == null) {
            return new ArrayList<UserCount>();
        }else{
            ArrayList<UserCount> returnList = new ArrayList<>();
            for (User user : this.participanList) {
                SuccessAmount result = user.isAvailable(activity);

                if (result.isTrue()) {
                    UserCount data = new DataPackage();
                    data.setUser(user);
                    data.setCount(result.amount());
                    returnList.add(data);
                }

            }
            returnList.sort(new CountSorting());

            return returnList;
        }


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
}
