package app;

import domain.User;

public class UserInfo {
    private String userId;

    public UserInfo(User user){
        this.userId = user.getUserId();
    }

    public UserInfo(){}

    public void setId(String string) {
        this.userId = string;
    }
}
