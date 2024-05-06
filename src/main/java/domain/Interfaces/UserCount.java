package domain.Interfaces;


import domain.Classes.User;
/*
 @Author: Niklas Emil Lysdal
 */
public interface UserCount extends Count, FinalUserCount {

    User getUser();

    void setUser(User u);
}

