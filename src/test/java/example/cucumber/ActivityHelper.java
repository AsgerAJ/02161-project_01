package example.cucumber;

import domain.Activity;

import java.util.ArrayList;

public class ActivityHelper {
    private Activity activity;
    private ArrayList<Activity> exampleActivityList = new ArrayList<>();
    public ActivityHelper (){

    }
    public void setActivity(Activity a){this.activity=a;}

    public Activity getActivity() {return activity;}

    public ArrayList<Activity> getExampleActivityList() {
        return exampleActivityList;
    }
    public ArrayList<Activity> getExampleActivities(int amount,int budget) {
        for (int i = 1; i<=amount;i++) {
            exampleActivityList.add(new Activity("exampleActivity"+i,budget));
        }
        return this.exampleActivityList;
    }
}
