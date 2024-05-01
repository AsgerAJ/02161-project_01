package example.cucumber.helpers;

import app.UserInfo;
import domain.Classes.User;
import domain.Interfaces.UserCount;

import java.util.ArrayList;

public class UserHelper {
    private User user;
    private ArrayList<User> exampleUserList = new ArrayList<>();
    private ArrayList<UserCount> availableUserList;
    private UserInfo userInfo;

    public UserHelper(){}

    public void setUser(User u) {
        this.user=u;
        this.userInfo=new UserInfo(user);
    }
    public User getUser() {
        return this.user;
    }
    public UserInfo getUserInfo() {return this.userInfo;}



    public ArrayList<User> getExampleUsers(int amount) {


        for (int i = 1; i<=amount;i++) {

            this.exampleUserList.add(new User("U"+i));

        }
        return this.exampleUserList;
    }

    public ArrayList<User> getExampleUsersList() {
        return this.exampleUserList;
    }

    public void setAvailableUserList(ArrayList<UserCount> availableUserList) {this.availableUserList=availableUserList;}
    public ArrayList<UserCount> getAvailableUserList() {return this.availableUserList;}
}
