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
    //-----Get Methods--------------------------------------------------------------------
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



    public ArrayList<String> getActivityList() {
        return new ArrayList<>(activityList.stream().map(a->a.getName()).toList());

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
    public Calendar getDeadlineCopy() {
        return (Calendar) this.deadline.clone();
    }
    public Calendar getStartdateCopy(){
        return (Calendar) this.startDate.clone();
    }

    //-----Miscellaneous-------------------------------------------------------------------------
    public String completionPercentageString(){
        if(this.getActivityList() != null){
            double completionPercentage = 0;

            for(int i = 0; i < this.getActivityList().size(); i++){
                if(this.activityList.get(i).getStatus()){
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

            return "Project " + progressBarBuilder.toString() + " " +(completionPercentage * 100) + "% complete";
        }else{
            return "Project □□□□□□□□□□□□□□□□□□□□ 0% complete";
        }

    }





}
