package app;

import domain.Classes.User;

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
