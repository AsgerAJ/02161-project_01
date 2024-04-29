package domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Activity extends PeriodEvent{
    private Calendar startDate = null;
    private Calendar deadline = null;
    private double budgetTime;
    private boolean isComplete = false;
    private HashMap<String, Double> timeMap = new HashMap<String, Double>();
    private double totalHours = 0;
    private ArrayList<User> participantList = new ArrayList<User>();


    public Activity(String name, double budgetTime){
        super(name);
        this.budgetTime = budgetTime;
    }

    public String getName(){return super.getName();}

    public void setStatus(boolean status){
        this.isComplete = status;
    }

    public boolean getStatus(){return this.isComplete;}

    public boolean isOverdue(Calendar today){
        return today.after(this.deadline);
    }

    public HashMap<String, Double> getTimeMap(){return this.timeMap;}
    public boolean isOverBudget(){return this.totalHours > this.budgetTime;}

    public void setStartDate(Calendar cal){this.startDate = cal;}

    public void setDeadline(Calendar cal){this.deadline = cal;}



    public ArrayList<User> getParticipantList() {return this.participantList;}

    public void logTime(double workedTime, User user){
        String userId = user.getUserId();
        if (timeMap.containsKey(userId)) {
            timeMap.put(userId, timeMap.get(userId) + workedTime);
        } else {
            timeMap.put(userId, + workedTime);
        }


        this.totalHours += workedTime;
    }

    public double getTotalHours(){
        return this.totalHours;
    }


    @Override
    public Calendar getDeadline() {
        return this.deadline;
    }

    @Override
    public Calendar getStartdate() {
        return this.startDate;
    }




    public double getBudgetTime() {
        return this.budgetTime;
    }
    public void assignUser(User u ) {
        this.participantList.add(u);
        u.assignActivity(this);
    }
}
