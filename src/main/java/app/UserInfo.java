package app;

import domain.Classes.User;

/*
    @author: Asger Allin Jensen
 */
public class UserInfo {
    private String userId;
    public UserInfo(User user){ // Author: Asger Allin Jensen
        this.userId = user.getUserId();
    }
    //------ Get methods----------------------------------------------------
    public UserInfo(){} // Author: Asger Allin Jensen

    public String getUserId(){ // Author: Asger Allin Jensen
        return this.userId;
    }


}
