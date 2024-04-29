package app;

import domain.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class App { // Implementer javafx senere, hvis nødvendig

    private ArrayList<User> userList = new ArrayList<User>();
    private User currentUser;

    private DateServer dateServer = new DateServer();

    private ArrayList<Project> projectRepository = new ArrayList<>();

    private int projectAmount = 1;
    public App(){
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
        String checkId = id;
        if(id.length()>4){
            id = id.substring(0,4);
        }else{
            while(id.length() < 4){
                id += 'x';
            }
        }

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

    public Project getProjectFromID(String name){
        for (Project project : this.projectRepository) {
            if (Objects.equals(project.getID(), name)) {
                return project;
            }
        }
        return null;
    }

    public Project createProject(String projectName) {
        Project p = new Project(projectName,this.dateServer.getDate(),this.projectAmount);


        if (currentUser != null) {
            p.assignUser(this.currentUser);
            p.setProjectLeader(this.currentUser);
        }
        //else must have signed in user(?)
        this.projectRepository.add(p);
        projectAmount++;
        return p;
    }
    public boolean hasProjectWithTitle(String t) {
        for (Project p : projectRepository) {
            if (p.getName().equals(t)) {
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

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void assignUserToProject(String userId, ProjectInfo pi) {
        try {
            User u = getUserFromId(userId);
            Project p = getProjectFromID(pi.getProjectID());
            p.assignUser(u);

        } catch (UserIdDoesNotExistExeption e) {
            e.printStackTrace();

        }

    }

    public void assignUserToActivity(String userId, ActivityInfo ai) {
        try {
            User u = getUserFromId(userId);
            Project p = getProjectFromID(ai.getParentProjectId());
            Activity a = p.getActivityFromName(ai.getActivityName());
            a.assignUser(u);

        } catch (UserIdDoesNotExistExeption e) {
            e.printStackTrace();

        }
    }

    public String getActualId(String userId) throws UserIdDoesNotExistExeption {
        return getUserFromId(userId).getUserId();
    }

    public String getRegisteredUsers(){
        String outputstring = getUserList().get(0).getUserId();
        for (int i = 1; i < getUserList().size(); i++){
            outputstring += ", " + getUserList().get(i).getUserId();
        }
        return (outputstring);
    }

    public Project getProjectFromIndex(int index) {
        if(index <= projectRepository.size()){
            return projectRepository.get(index);
        }
        return null;
    }

    public String getProjectListString(){
        if(getCurrentUser().getAssignedProjects().size() != 0){
            String outputstring = "";
            int index = 1;
            for(Project project : getCurrentUser().getAssignedProjects()){
                outputstring += "\n" + index +" : " + "Project id: " + project.getProjectID() + " Project name: " + project.getName();
                index++;
            }
            return outputstring + "\n";
        }
        return("No projects found");
    }

    public Activity getActivityFromIndex(ProjectInfo currentproject, int index) {
        Project p = getProjectFromID(currentproject.getProjectID());
        if(index <= p.getActivityList().size()){
            return p.getActivityList().get(index);
        }
        return null;
    }

    public void createNewActivity(String newActivityName, double numberIn, ProjectInfo currentProject) {
        Project p = getProjectFromID(currentProject.getProjectID());
        Activity a = new Activity(newActivityName, numberIn);
        p.createNewActivity(a);
    }

    public String getActivityListString(ProjectInfo currentProject) {
        Project p = getProjectFromID(currentProject.getProjectID());
        if(p.getActivityList().size() != 0){
            String outputstring = "";
            int index = 1;
            for(Activity activity : p.getActivityList()){
                outputstring += "\n" + index +" : " + "Activity name: " + activity.getName() + " Budget time: " + activity.getBudgetTime();
                index++;
            }
            return outputstring + "\n";
        }
        return("No activities found");
    }

    public void setProjectStartDate(Calendar c, ProjectInfo currentProject) throws InvalidDateException {
        Project p = getProjectFromID(currentProject.getProjectID());
        try {
            p.setStartDate(c);
        } catch (InvalidDateException e) {
            e.printStackTrace();
        }
    }

    public void setProjectDeadline(Calendar c, ProjectInfo currentProject) {
        Project p = getProjectFromID(currentProject.getProjectID());
        try {
            p.setDeadline(c);
        } catch (InvalidDateException e) {
            e.printStackTrace();
        }
    }
    public String getProjectCompletionString(ProjectInfo currentProject) {
        Project p = getProjectFromID(currentProject.getProjectID());
        return p.completionPercentage();
    }

    public void logTimeOnActivity(double workedTime, User currentUser, ActivityInfo currentActivity) {
        Project p = getProjectFromID(currentActivity.getParentProjectId());
        Activity a = p.getActivityFromName(currentActivity.getActivityName());
        a.logTime(workedTime, currentUser);
    }

    public void ChangeCompletenessOfActivity(boolean b, ActivityInfo currentActivity) {
        Project p = getProjectFromID(currentActivity.getParentProjectId());
        Activity a = p.getActivityFromName(currentActivity.getActivityName());
        a.setStatus(b);
    }
}