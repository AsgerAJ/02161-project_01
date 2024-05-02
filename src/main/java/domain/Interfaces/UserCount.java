package domain.Interfaces;


import domain.Classes.User;

public interface UserCount extends Count, FinalUserCount {

    public User getUser();

    public void setUser(User u);







}

