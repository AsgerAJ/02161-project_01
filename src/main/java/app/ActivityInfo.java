package app;

import domain.Activity;

public class ActivityInfo {
    private String activityName;

    public ActivityInfo(Activity activity){
        this.activityName = activity.getName();
    }
}
