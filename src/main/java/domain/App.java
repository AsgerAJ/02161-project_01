package domain;

import app.AUserIsAlreadyLoggedInException;
import app.UserIdAlreadyInUseExeption;
import app.UserIdDoesNotExistExeption;

import java.util.ArrayList;
import java.util.Scanner;

public class App { // Implementer javafx senere, hvis nødvendig

    ArrayList<User> userList = new ArrayList<User>();
    private User currentUser;
    private Scanner inputscanner = new Scanner(System.in);
    private DateServer dateServer = new DateServer();

    private ArrayList<Project> projectRepository = new ArrayList<>();

    private int projectAmount = 1;
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

    public User registerUser(String userId) throws UserIdAlreadyInUseExeption {
        while (userId.length()<4) {
            userId += "x";
        }
        String edited = userId.substring(0,1).toUpperCase() + userId.substring(1,4);
        User u = new User(edited);
        if(!hasUserWithID(edited)){
            this.userList.add(u);
            System.out.print(userList);
        } else {

            throw new UserIdAlreadyInUseExeption("UserId already in use");
        }
        return u;
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

    public void logInUser(String id) throws UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException {

        if (this.currentUser!=null) {
            throw new AUserIsAlreadyLoggedInException("A user is already logged in");
        }
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

    public void setDateServer(DateServer d) {
        this.dateServer=d;
    }


    public Project createProject(String projectName) {
        Project p = new Project(projectName,this.dateServer.getDate(),this.projectAmount);


        if (currentUser != null) {
            p.assignUser(this.currentUser);
        }
        //else must have signed in user(?)
        this.projectRepository.add(p);
        projectAmount++;
        return p;
    }
    public boolean hasProjectWithTitle(String t) {
        for (Project p : projectRepository) {
            if (p.getTitle().equals(t)) {
                return true;
            }
        }
        return false;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }
}