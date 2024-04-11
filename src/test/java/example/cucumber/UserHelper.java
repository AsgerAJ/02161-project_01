package example.cucumber;

import domain.User;

public class UserHelper {
    private User user;

    public UserHelper(){}

    public void setUser(User u) {
        this.user=u;
    }
    public User getUser() {
        return this.user;
    }
}
