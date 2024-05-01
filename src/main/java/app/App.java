package app;

import domain.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

/* This class works both as an object itself, but also as a facade between the viewer class and the Business logic*/
public class App { // Implementer javafx senere, hvis nødvendig

    private ArrayList<User> userList = new ArrayList<>();
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


    public void setDateServer(DateServer d) {
        this.dateServer=d;
    }

    public Project getProjectFromID(String id){
        ArrayList<Project> matchingProjects = projectRepository.stream().filter(p->p.getProjectID().equals(id)).collect(Collectors.toCollection(ArrayList::new));
        if (!matchingProjects.isEmpty()) {
            return matchingProjects.get(0);
        } else {
            return null;
        }


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



    public ArrayList<User> getUserList() {
        return userList;
    }

    public void assignUserToProject(String userId, ProjectInfo pi) {
        try {
            User u = getUserFromId(userId);
            Project p = getProjectFromID(pi.getProjectID());
            p.assignUser(u);

        } catch (UserIdDoesNotExistExeption e) {
            System.out.println(e.getMessage());

        }

    }

    public void removeUserFromProject(String userId, ProjectInfo pi) {
        try {
            User u = getUserFromId(userId);
            Project p = getProjectFromID(pi.getProjectID());
            p.removeUser(u);

        } catch (UserIdDoesNotExistExeption e) {
            System.out.println(e.getMessage());

        }

    }

    public void assignUserToActivity(String userId, ActivityInfo ai) {
        try {
            User u = getUserFromId(userId);
            Project p = getProjectFromID(ai.getParentProjectId());
            Activity a = p.getActivityFromName(ai.getActivityName());
            a.assignUser(u);

        } catch (UserIdDoesNotExistExeption e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserFromActivity(String userId, ActivityInfo ai) {
        try {
            User u = getUserFromId(userId);
            Project p = getProjectFromID(ai.getParentProjectId());
            Activity a = p.getActivityFromName(ai.getActivityName());
            a.removeUser(u);

        } catch (UserIdDoesNotExistExeption e) {
            System.out.println(e.getMessage());
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
        if(!getCurrentUser().getAssignedProjects().isEmpty()){
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
        if(!p.getActivityList().isEmpty()){
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

    public String timeMapToString(ActivityInfo activityInfo) {
        Project p = this.getProjectFromID(activityInfo.getParentProjectId());
        Activity a = p.getActivityFromName(activityInfo.getActivityName());
        String outputstring = "";
        if (a.getTimeMap().isEmpty()) {
            return outputstring;
        } else {
            String key;
            for(Iterator var5 = a.getTimeMap().keySet().iterator(); var5.hasNext(); outputstring = outputstring + key + " : " + a.getTimeMap().get(key) + " Hours\n") {
                key = (String)var5.next();
            }

            return outputstring;
        }
    }



    public void setProjectStartDate(Calendar c, ProjectInfo currentProject) throws InvalidDateException {
        Project p = getProjectFromID(currentProject.getProjectID());
        try {
            p.setStartDate(c);
        } catch (InvalidDateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setProjectDeadline(Calendar c, ProjectInfo currentProject) {
        Project p = getProjectFromID(currentProject.getProjectID());
        try {
            p.setDeadline(c);
        } catch (InvalidDateException e) {
            System.out.println(e.getMessage());
        }
    }
    public String getProjectCompletionString(ProjectInfo currentProject) {
        Project p = getProjectFromID(currentProject.getProjectID());
        return p.completionPercentage();
    }

    public void logTimeOnActivity(double workedTime, ActivityInfo currentActivity) {
        Project p = getProjectFromID(currentActivity.getParentProjectId());
        Activity a = p.getActivityFromName(currentActivity.getActivityName());
        a.logTime(workedTime, this.currentUser);
    }

    public void ChangeCompletenessOfActivity(boolean b, ActivityInfo currentActivity) {
        Project p = getProjectFromID(currentActivity.getParentProjectId());
        Activity a = p.getActivityFromName(currentActivity.getActivityName());
        a.setStatus(b);
    }

    public void enableDemoConfig() throws UserIdAlreadyInUseExeption, UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException, InvalidDateException {

        registerUser("Huba");
        registerUser("LOVR");
        registerUser("ASGE");
        registerUser("NIKL");
        registerUser("NIKO");

        //Log in as Huba
        logInUser("Huba");
        createProject("Soft. eng. project");
        Project p = getProjectFromTitle("Soft. eng. project");
        p.createNewActivity(new Activity("Design", 10));
        p.createNewActivity(new Activity("Implementation", 20));
        p.createNewActivity(new Activity("Testing", 10));
        p.createNewActivity(new Activity("Documentation", 10));
        p.assignUser(getUserFromId("LOVR"));
        p.assignUser(getUserFromId("ASGE"));
        p.assignUser(getUserFromId("NIKL"));
        p.assignUser(getUserFromId("NIKO"));
        p.setStartDate(new Calendar.Builder().setDate(2024, 3, 1).build());
        p.setDeadline(new Calendar.Builder().setDate(2024, 4, 6).build());

        Activity a = p.getActivityFromName("Design");
        a.assignUser(getUserFromId("NIKL"));
        a.assignUser(getUserFromId("NIKO"));
        a.logTime(5, getUserFromId("NIKL"));
        a.logTime(5, getUserFromId("NIKO"));
        a.setStatus(true);
        a = p.getActivityFromName("Implementation");
        a.assignUser(getUserFromId("LOVR"));
        a.assignUser(getUserFromId("ASGE"));
        a.logTime(10, getUserFromId("LOVR"));
        a.logTime(2, getUserFromId("ASGE"));
        a.setStatus(true);
        a = p.getActivityFromName("Testing");
        a.assignUser(getUserFromId("NIKL"));
        a.assignUser(getUserFromId("NIKO"));
        a.assignUser(getUserFromId("LOVR"));
        a.assignUser(getUserFromId("ASGE"));
        a.assignUser(getUserFromId("HUBA"));
        a.logTime(5, getUserFromId("NIKL"));
        a.logTime(7, getUserFromId("NIKO"));
        a.logTime(10, getUserFromId("LOVR"));
        a.logTime(9, getUserFromId("ASGE"));
        a.logTime(5, getUserFromId("HUBA"));

        a = p.getActivityFromName("Documentation");
        a.assignUser(getUserFromId("NIKL"));
        a.assignUser(getUserFromId("HUBA"));
        a.logTime(5, getUserFromId("NIKL"));
        a.logTime(7, getUserFromId("HUBA"));

        createProject("Yoga");
        p = getProjectFromTitle("Yoga");
        p.createNewActivity(new Activity("Yoga", 10));
        p.createNewActivity(new Activity("Meditation", 20));
        a = p.getActivityFromName("Yoga");
        a.logTime(5, getUserFromId("Huba"));


        logOut();

        //Log in as Asger
        logInUser("ASGE");
        createProject("Exam Preperation");
        p = getProjectFromTitle("Exam Preperation");
        p.createNewActivity(new Activity("Math 1b", 40));
        p.createNewActivity(new Activity("Physics", 20));
        p.createNewActivity(new Activity("Algorithms and datastructures", 30));
        p.createNewActivity(new Activity("Software eng.", 70));

        a = p.getActivityFromName("Math 1b");
        a.setStatus(true);

        logOut();

        //Log in as Lovro

        logInUser("LOVR");
        createProject("Coding");
        p = getProjectFromTitle("Coding");
        p.createNewActivity(new Activity("Java (•́︵•̀)", 40));
        p.createNewActivity(new Activity("Python", 20));
        p.createNewActivity(new Activity("C++", 30));
        p.createNewActivity(new Activity("C#", 70));

        logOut();

        //Log in as Niklas

        logInUser("NIKL");
        createProject("Git gud");
        p = getProjectFromTitle("Git gud");
        p.createNewActivity(new Activity("Quick Scoping", 40));
        p.createNewActivity(new Activity("No scoping", 20));
        p.createNewActivity(new Activity("360 no scoping", 30));

        a = p.getActivityFromName("No Scoping");
        a.setStatus(true);

        logOut();

        //Log in as Nikolaj
        logInUser("NiKO");
        createProject("Rapport 2");
        p = getProjectFromTitle("Rapport 2");
        p.createNewActivity(new Activity("Rapport", 40));
        p.createNewActivity(new Activity("Rapport 2", 20));
        p.createNewActivity(new Activity("Rapport 2 electric boogaloo", 30));
        p.createNewActivity(new Activity("Rapport 4", 70));

        logOut();
    }

    public Project getProjectFromTitle(String title) {
        for (Project p : projectRepository) {
            if (p.getName().equalsIgnoreCase(title)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<String> findFreeEmployee(ActivityInfo aci) {
        Project p = getProjectFromID(aci.getParentProjectId());
        Activity a = p.getActivityFromName(aci.getActivityName());
        ArrayList <UserCount> avU = p.findFreeEmployee(a);
        return avU.stream().map(entry -> entry.getUser().getUserId() + ": " + entry.getCount() + " activities overlapping").collect(Collectors.toCollection(ArrayList::new));


    }


    public void setActivityStartDateFromInfo(ActivityInfo acI, Calendar c) {
        getProjectFromID(acI.getParentProjectId()).getActivityFromName(acI.getActivityName()).setStartdate(c);
    }
    public void setActivityDeadlineFromInfo(ActivityInfo acI, Calendar c) {
        getProjectFromID(acI.getParentProjectId()).getActivityFromName(acI.getActivityName()).setDeadline(c);
    }
}