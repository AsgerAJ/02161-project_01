package app;

import domain.User;

public class UserInfo {
    private String userId;

    public UserInfo(User user){
        this.userId = user.getUserId();
    }
}
