package app;

import domain.AUserIsAlreadyLoggedInException;
import domain.UserIdAlreadyInUseExeption;
import domain.UserIdDoesNotExistExeption;
import domain.DateServer;
import domain.Project;
import domain.User;

import java.util.ArrayList;
import java.util.Objects;

public class App { // Implementer javafx senere, hvis nødvendig
    private static App instance =new App();

    private App() {};
    public static App getInstance() {
        return instance;
    }

    ArrayList<User> userList = new ArrayList<User>();
    private User currentUser;

    private DateServer dateServer = new DateServer();

    private ArrayList<Project> projectRepository = new ArrayList<>();

    private int projectAmount = 1;


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
        String edited = (userId.substring(0,1).toUpperCase() + userId.substring(1,4).toUpperCase());
        User u = new User(edited);
        if(!hasUserWithID(edited)){
            this.userList.add(u);
        } else {

            throw new UserIdAlreadyInUseExeption("UserId already in use");
        }
        return u;
    }

    public  User getUserFromId(String id) throws UserIdDoesNotExistExeption{
        for (User u : userList){
            if(u.getUserId().equals(id.toUpperCase())){
                return u;
            }
        }
        throw new UserIdDoesNotExistExeption("No user with UserId exists");
    }

    public void removeUserWithId(String id){
        userList.removeIf(u -> u.getUserId().equals(id.toUpperCase()));
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

    public Project getProjectFromName(String name){
        for (Project project : this.projectRepository) {
            if (Objects.equals(project.getTitle(), name)) {
                return project;
            }
        }
        return null;
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

    public ArrayList<Project> getProjectRepository() {
        return projectRepository;
    }
}