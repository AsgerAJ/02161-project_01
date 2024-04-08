package domain;

import app.UserIdAlreadyInUseExeption;
import app.UserIdDoesNotExistExeption;

import java.util.ArrayList;
import java.util.Scanner;

public class App { // Implementer javafx senere, hvis nødvendig

    ArrayList<User> userList = new ArrayList<User>();
    private User currentUser;
    private Scanner inputscanner = new Scanner(System.in);

    public App(){
    }

    public void registerUser() throws UserIdAlreadyInUseExeption {
        System.out.println("Enter Userid");
        String unedited = inputscanner.next();
        String edited = unedited.substring(0,0).toUpperCase() + unedited.substring(1,3);
        if(!hasUserWithID(edited)){
            this.userList.add(new User(edited));
        } else {
            throw new UserIdAlreadyInUseExeption("UserId already in use");
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

    public void registerUser(String userId) throws UserIdAlreadyInUseExeption {
        String edited = userId.substring(0,1).toUpperCase() + userId.substring(1,4);
        if(!hasUserWithID(edited)){
            this.userList.add(new User(edited));
            System.out.print(userList);
        } else {

            throw new UserIdAlreadyInUseExeption("UserId already in use");
        }
    }

    public  User getUserFromId(String id){
        for (User u : userList){
            if(u.getUserId().equals(id)){
                return u;
            }
        }
        return null;
    }

    public void removeUserWithId(String id){
        userList.removeIf(u -> u.getUserId().equals(id));
    }

    public boolean loggedInStatus(){
        return this.currentUser != null;
    }

    public void logInUser(String id) throws UserIdDoesNotExistExeption {
        User temp = getUserFromId(id);
        if(temp != null){
            currentUser = temp;
        }else{
            throw new UserIdDoesNotExistExeption("No user with UserId exists");
        }
    }

    public String getCurrentUserId(){
        return this.currentUser.getUserId();
    }

    public void logOut(){
        this.currentUser = null;
    }

    public ArrayList<User> getUsers() {
        return userList;
    }
}