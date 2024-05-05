package domain.Interfaces;


import domain.Classes.User;

public interface UserCount extends Count, FinalUserCount {

    User getUser();

    void setUser(User u);







}

