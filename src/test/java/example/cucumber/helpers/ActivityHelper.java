package example.cucumber.helpers;

import app.ActivityInfo;
import domain.Classes.Activity;

import java.util.ArrayList;

public class ActivityHelper {
    private Activity activity;
    private ArrayList<Activity> exampleActivityList = new ArrayList<>();
    private ActivityInfo aI;
    public ActivityHelper (){

    }
    public void setActivity(Activity a){
        this.activity=a;
        this.aI=new ActivityInfo(a);}


    public Activity getActivity() {return activity;}

    public ArrayList<Activity> getExampleActivityList() {
        return exampleActivityList;
    }
    public ArrayList<Activity> getExampleActivities(int amount,int budget,String parentProjectId) {
        for (int i = 1; i<=amount;i++) {
            exampleActivityList.add(new Activity("exampleActivity"+i,budget,parentProjectId));
        }
        return this.exampleActivityList;
    }

    public void setActivityInfo(ActivityInfo a) {
        this.aI=a;
    }
    public ActivityInfo getActivityInfo(){
        return this.aI;
    }
}
