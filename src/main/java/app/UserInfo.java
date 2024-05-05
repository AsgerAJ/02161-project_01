package app;

import domain.Classes.PeriodEvent;
import domain.Classes.Project;
import domain.Classes.User;

import java.util.ArrayList;

public class UserInfo {
    private String userId;
    public UserInfo(User user){
        this.userId = user.getUserId();
    }
    //------ Get methods----------------------------------------------------
    public UserInfo(){}

    public String getUserId(){
        return this.userId;
    }


}
