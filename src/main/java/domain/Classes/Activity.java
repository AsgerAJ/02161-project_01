package domain.Classes;

import app.ActivityInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


/*
    @author: Nikolaj Vorndran Thygesen
 */
public class Activity extends PeriodEvent {
    private final double budgetTime;
    private boolean isComplete = false;
    private final HashMap<String, Double> timeMap = new HashMap<String, Double>();
    private double totalHours = 0;
    private final ArrayList<User> participantList = new ArrayList<User>();
    private final String parentProjectID;

    public Activity(String name, double budgetTime, String parentProjectID) { // Author: Nikolaj Vorndran Thygesen
        super(name);
        this.budgetTime = budgetTime;
        this.parentProjectID=parentProjectID;
    }
    //------Get Methods ----------------------------------------------------------------------------
    public void setStatus(boolean status) {
        this.isComplete = status;
    } // Author: Lovro Antic

    public boolean getStatus() {return this.isComplete;} // Author Lovro Antic

    public HashMap<String, Double> getTimeMap() {return this.timeMap;} // Author: Lovro Antic



    public double getBudgetTime() {return this.budgetTime;} // Author: Nikolaj Vorndran Thygesen

    public ArrayList<User> getParticipantList() {return this.participantList;} // Author Lovro Antic
    public String getParentProjectID(){return this.parentProjectID;} // Author: Lovro Antic

    //-----Checks ------------------------------------------------------------------------------
    public boolean isOverdue(Calendar today) { // Author: Asger Allin Jensen
        return !isComplete && today.after(this.getDeadline()); //not overdue if complete
    }

    public boolean isOverBudget() {
        return this.totalHours > this.budgetTime;
    } // Author: Asger Allin Jensen


    //------ Functional--------------------------------------------------------------------------

    public void logTime(double workedTime, User user) { // Author: Nikolaj Vorndran Thygesen
        String userId = user.getUserId();
        if (timeMap.containsKey(userId)) {
            timeMap.put(userId, timeMap.get(userId) + workedTime);
        } else {
            timeMap.put(userId, workedTime);
        }

        this.totalHours += workedTime;
    }

    public void assignUser(User u) { // Author: Asger Allin Jensen
        this.participantList.add(u);
        u.assignActivity(this);
    }

    public void removeUser(User u) { // Author: Asger Allin Jensen
        this.participantList.remove(u);
        u.removeActivity(this);
    }
    //----As info-----------------------------------------------------------------------------------
    public ActivityInfo asInfo() {
        return new ActivityInfo(this);
    } // Author: Niklas Emil Lysdal



}
