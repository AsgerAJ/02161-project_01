package domain;

import java.util.ArrayList;
import java.util.Scanner;

public class App { // Implementer javafx senere, hvis nødvendig

    ArrayList<User> userList = new ArrayList<User>();
    private User currentUser;
    private Scanner inputscanner = new Scanner(System.in);

    public App(){
    }

    public void registerUser(){
        System.out.println("Enter Userid");
        String unedited = inputscanner.next();
        String edited = unedited.substring(0,0).toUpperCase() + unedited.substring(1,3);
        if(hasUserWithID(edited)){
            this.userList.add(new User(edited));
        } else {
            System.out.println("Username " + " Already in use");
        }
    }

    public boolean hasUserWithID(String id) {
        for (User u : userList){
            if(u.getUserId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public void registerUser(String userId){
        String edited = userId.substring(0,0).toUpperCase() + userId.substring(1,3);
        if(hasUserWithID(edited)){
            this.userList.add(new User(edited));
        } else {
            System.out.println("Username " + " Already in use");
        }
    }

    public void removeUserWithId(String id){
        userList.removeIf(u -> u.getUserId().equals(id));
    }

    public ArrayList<User> getUsers() {
        return userList;
    }
}