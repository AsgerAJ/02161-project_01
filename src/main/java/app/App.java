package app;

import domain.Classes.*;
import domain.Interfaces.FinalUserCount;
import domain.Interfaces.UserCount;
import domain.exceptions.AUserIsAlreadyLoggedInException;
import domain.exceptions.InvalidDateException;
import domain.exceptions.UserIdAlreadyInUseExeption;
import domain.exceptions.UserIdDoesNotExistExeption;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.stream.Collectors;

/* This class works both as an object itself, but also as a facade between the viewer class and the other Business logic*/
public class App {
    private final ArrayList<User> userList = new ArrayList<>();
    private User currentUser;
    private DateServer dateServer = new DateServer();
    private final ArrayList<Project> projectRepository = new ArrayList<>();
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



    //------Get methods --------------------------------------------------------------------------------------
    public String getCurrentUserId(){
        return this.currentUser.getUserId();
    }
    public User getUserFromId(String id) throws UserIdDoesNotExistExeption {
        assert id != null;                                                              //1
        for (User u : userList){                                                        //2
            if(u.getUserId().equals(id.toUpperCase())){                                 //3
                assert userList.contains(u);                                            //4
                assert u.getUserId().equals(id.toUpperCase());                          //5
                return u;                                                               //6
            }
        }
        throw new UserIdDoesNotExistExeption("No user with UserId exists");       //7
    }

    private Project getProjectFromTitle(String title) {
        for (Project p : projectRepository) {
            if (p.getName().equalsIgnoreCase(title)) {
                return p;
            }
        }
        return null;
    }

    private Project getProjectFromID(String id){
        ArrayList<Project> matchingProjects = projectRepository.stream().filter(p->p.getProjectID().equals(id)).collect(Collectors.toCollection(ArrayList::new));
        if (!matchingProjects.isEmpty()) {
            return matchingProjects.get(0);
        } else {
            return null;
        }


    }
    
    public ProjectInfo getProjectInfoFromID(String id) {
        ArrayList<Project> matchingProjects = projectRepository.stream().filter(p->p.getProjectID().equals(id)).collect(Collectors.toCollection(ArrayList::new));
        if (!matchingProjects.isEmpty()) {
            return matchingProjects.get(0).asInfo();
        } else {
            return null;
        }
    }

    //-------Log in --------------------------------------------------------------------------------
    public boolean loggedInStatus(){
        return this.currentUser != null;
    }

    public void logInUser(String id) throws UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException { 
        assert id != null;
        if (this.currentUser!=null) {                                                     
            throw new AUserIsAlreadyLoggedInException("A user is already logged in");          
        }
        User temp = getUserFromId(id);                                                                
        if(temp != null){                                                                         
            this.currentUser = temp;                                                                       
        }else{                                                                                        
            throw new UserIdDoesNotExistExeption("No user with UserId exists");                
        }
        assert this.getUserList().contains(currentUser) || this.currentUser == null;
    }
    
    public void logOut(){
        this.currentUser = null;
    }
    //------- Manipulate users ------------------------------------------------------------------
    public User registerUser(String userId) throws UserIdAlreadyInUseExeption {
        assert userId != null;
        String edited = createUserId(userId);
        User u = new User(edited);
        if(!hasUserWithID(edited)){
            assert u.getUserId().equals(edited);
            this.userList.add(u);
            assert userList.contains(u);
        } else {
            throw new UserIdAlreadyInUseExeption("UserId already in use");
        }
        return u;
    }
    public User quickRegisterUser(String userId) throws UserIdAlreadyInUseExeption {
        User u = new User(userId);
        if(!hasUserWithID(userId)){
            this.userList.add(u);
        } else {
            throw new UserIdAlreadyInUseExeption("UserId already in use");
        }
        return u;
    }
    public void removeUserWithId(String id){
        userList.removeIf(u -> u.getUserId().equals(id.toUpperCase()));
    }
    public User getCurrentUser() {
        return this.currentUser;
    }



    public boolean hasProjectWithTitle(String t) {
        for (Project p : projectRepository) {
            if (p.getName().equals(t)) {
                return true;
            }
        }
        return false;
    }
    public String getActualId(String userId) throws UserIdDoesNotExistExeption {
        return getUserFromId(userId).getUserId();
    }
    public String getRegisteredUsers(){
        StringBuilder outputstring = new StringBuilder(getUserList().get(0).getUserId());
        for (int i = 1; i < getUserList().size(); i++){
            outputstring.append(", ").append(getUserList().get(i).getUserId());
        }
        return (outputstring.toString());
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void registerLeave(String name, Calendar start, Calendar end) {
        currentUser.registerLeave(name, start, end);
    }

    public void removeLeave(String name, Calendar start, Calendar end) {
        currentUser.removeLeave(name, start, end);
    }


    //FOLLOWING METHODS SHOULD HAVE BEEN MOVED INFO CLASSES OF VIEWER CLASS TO ADHERE TO MVC PATTERN.
    public String getProjectListString(){
        if(!getCurrentUser().getAssignedProjects().isEmpty()){
            StringBuilder outputstring = new StringBuilder();
            int index = 1;
            for(Project project : getCurrentUser().getAssignedProjects()){
                outputstring.append("\n").append(index).append(" : ").append("Project id: ").append(project.getProjectID()).append(" Project name: ").append(project.getName());
                index++;
            }
            return outputstring + "\n";
        }
        return("No projects found");
    }

    public String getActivityListString(ProjectInfo currentProject) {
        Project p = getProjectFromID(currentProject.getProjectID());
        assert p != null;
        if(!p.getActivityList().isEmpty()){
            StringBuilder outputstring = new StringBuilder();
            int index = 1;
            for(Activity activity : p.getActivityList()){
                outputstring.append("\n").append(index).append(" : ").append("Activity name: ").append(activity.getName()).append(" Budget time: ").append(activity.getBudgetTime());
                index++;
            }
            return outputstring + "\n";
        }
        return("No activities found");
    }
    public String getCurrentUserActivityListString(){

        ArrayList<PeriodEvent> totalList = currentUser.getAssignedActivities();
        ArrayList<PeriodEvent> workList = new ArrayList<>(totalList.stream().filter(p->p instanceof Activity).toList());
        ArrayList<PeriodEvent> leaveList = new ArrayList<>(totalList.stream().filter(p->p instanceof Leave).toList());
        StringBuilder output = new StringBuilder();
        output.append("Work activities: \n");
        for (PeriodEvent p : workList) {
            output.append(p.getName());
            output.append(". ");
            output.append("week: ").append(p.getStartdate().get(Calendar.WEEK_OF_YEAR)).append(" to ").append(p.getDeadline().get(Calendar.WEEK_OF_YEAR)).append(". \n");
        }
        output.append("Leaves: \n");
        for (PeriodEvent p : leaveList) {
            output.append(p.getName());
            output.append(". ");
            //start date
            output.append("Period: ").append(p.getStartdate().get(Calendar.DAY_OF_MONTH)).append("/");
            output.append(p.getStartdate().get(Calendar.MONTH)+1).append("/").append(p.getStartdate().get(Calendar.YEAR)).append(" to ");
            //deadline
            output.append(p.getDeadline().get(Calendar.DAY_OF_MONTH)).append("/").append(p.getDeadline().get(Calendar.MONTH)+1).append("/").append(p.getDeadline().get(Calendar.YEAR));
            output.append(". \n");
        }
        return output.toString();

    }
    private Activity getActivityFromIndex(ProjectInfo currentproject, int index) {
        Project p = getProjectFromID(currentproject.getProjectID());
        assert p != null;
        return (index <= p.getActivityList().size()) ? p.getActivityList().get(index) : null;
    }

    public String createUserId(String userId) {
        StringBuilder userIdOutputString = new StringBuilder();
        userId = userId.replaceAll("\\d","");
        String[] splitString = userId.split(" ");
        switch(splitString.length) {
            case 1:
                for (int i = 0; i <= (splitString[0].length() > 4 ? 3 : splitString[0].length()-1); i++) {
                    String appendstring = "" + splitString[0].charAt(i);
                    userIdOutputString.append(appendstring);
                }
                break;
            case 2:
                for (int i = 0; i <= (splitString[0].length() < 2 ? 0 : 1); i++) {
                    String appendstring = "" + splitString[0].charAt(i);
                    userIdOutputString.append(appendstring);
                }
                for (int i = 0; i <= (splitString[1].length() < 2 ? 0 : 1); i++) {
                    String appendstring = "" + splitString[1].charAt(i);
                    userIdOutputString.append(appendstring);
                }
                break;
            default:
                for (int i = 0; i <= splitString.length-1; i++) {
                    String appendstring = "" + splitString[i].charAt(0);
                    userIdOutputString.append(appendstring);
                }
        }
        if (userIdOutputString.length()<4) {
            return userIdOutputString.toString().toUpperCase();
        } else {
            return  (userIdOutputString.toString().toUpperCase().substring(0,4));
        }

    }


    //----- Manipulate Project --------------------------------------------------------------------------

    public ProjectInfo getCurrentUserProjectsInfoFromID(String id) {
        ArrayList<Project> matchingProjects = projectRepository.stream().filter(p->p.getProjectID().equals(id)).collect(Collectors.toCollection(ArrayList::new));
        return (!matchingProjects.isEmpty() && currentUser.getAssignedProjects().contains(matchingProjects.get(0))) ? matchingProjects.get(0).asInfo() : null;
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
    public void assignUserToProject(String userId, ProjectInfo pi) throws UserIdDoesNotExistExeption {
            User u = getUserFromId(userId);
            Project p = getProjectFromID(pi.getProjectID());
        assert p != null;
        p.assignUser(u);
    }

    public void removeUserFromProject(String userId, ProjectInfo pi) throws UserIdDoesNotExistExeption {
            User u = getUserFromId(userId);
            Project p = getProjectFromID(pi.getProjectID());
        assert p != null;
        p.removeUser(u);
    }
    public boolean isProjectOverdue(ProjectInfo pi) {
        return getProjectFromID(pi.getProjectID()).isOverdue(this.dateServer.getDate());
    }

    public void changeCompletenessOfProject(boolean newStatus,ProjectInfo info) {
        getProjectFromID(info.getProjectID()).changeCompleteness(newStatus);

    }

    //------- Manipulate Activity ---------------------------------------------------------------------
    public void assignUserToActivity(String userId, ActivityInfo ai) throws UserIdDoesNotExistExeption {
            User u = getUserFromId(userId);
            Project p = getProjectFromID(ai.getParentProjectId());
        assert p != null;
        Activity a = p.getActivityFromName(ai.getActivityName());
            a.assignUser(u);


    }

    public void removeUserFromActivity(String userId, ActivityInfo ai) throws UserIdDoesNotExistExeption {
            User u = getUserFromId(userId);
            Project p = getProjectFromID(ai.getParentProjectId());
        assert p != null;
        Activity a = p.getActivityFromName(ai.getActivityName());
            a.removeUser(u);
    }

    public void createNewActivity(String newActivityName, double numberIn, ProjectInfo currentProject) {
        Project p = getProjectFromID(currentProject.getProjectID());
        Activity a = new Activity(newActivityName, numberIn,p.getProjectID());
        p.createNewActivity(a);
    }

    public void logTimeOnActivity(double workedTime, ActivityInfo currentActivity) {
        Project p = getProjectFromID(currentActivity.getParentProjectId());
        assert p != null;
        Activity a = p.getActivityFromName(currentActivity.getActivityName());
        a.logTime(workedTime, this.currentUser);
    }

    public void ChangeCompletenessOfActivity(boolean b, ActivityInfo currentActivity) {
        Project p = getProjectFromID(currentActivity.getParentProjectId());
        assert p != null;
        Activity a = p.getActivityFromName(currentActivity.getActivityName());
        a.setStatus(b);
    }
    public boolean isActivityOverdue(ActivityInfo info) {
        return getProjectFromID(info.getParentProjectId()).getActivityFromName(info.getActivityName()).isOverdue(dateServer.getDate());
    }
    public boolean isActivityOverBudget(ActivityInfo cai) {
        return getProjectFromID(cai.getParentProjectId()).getActivityFromName(cai.getActivityName()).isOverBudget();
    }


    //-----Find free employee------------------------------------------------------------------------------------------
    public ArrayList<FinalUserCount> findFreeEmployee(ActivityInfo aci) {
        Project p = getProjectFromID(aci.getParentProjectId());
        assert p != null;
        Activity a = p.getActivityFromName(aci.getActivityName());
        ArrayList <UserCount> avU = p.findFreeEmployee(a);
        return avU.stream().map(entry -> (FinalUserCount)entry).collect(Collectors.toCollection(ArrayList::new));


    }


    //----Set dates-----------------------------------------------------------------------------------------------------
    public void setDateServer(DateServer d) {
        this.dateServer=d;
    }
    public void setProjectStartDate(Calendar c, ProjectInfo currentProject) throws InvalidDateException {
        getProjectFromID(currentProject.getProjectID()).setStartDate(c);


    }

    public void setProjectDeadline(Calendar c, ProjectInfo currentProject) throws InvalidDateException {
        getProjectFromID(currentProject.getProjectID()).setDeadline(c);
    }

    public void setActivityStartDateFromInfo(ActivityInfo acI, Calendar c) throws InvalidDateException {
        getProjectFromID(acI.getParentProjectId()).getActivityFromName(acI.getActivityName()).setStartdate(c);
    }
    public void setActivityDeadlineFromInfo(ActivityInfo acI, Calendar c) throws InvalidDateException {
        getProjectFromID(acI.getParentProjectId()).getActivityFromName(acI.getActivityName()).setDeadline(c);

    }

    //get info's -------------------------------------------------------------------------------------------------------


    public ActivityInfo getActivityInfoFromIndex(ProjectInfo currentproject, int index) {
        try {
            return getActivityFromIndex(currentproject,index).asInfo();
        } catch (NullPointerException e) {
            return null;
        }
    }
    public ActivityInfo getActivityInfoFromName(ProjectInfo currentProject,String name){
        return getProjectFromID(currentProject.getProjectID()).getActivityFromName(name).asInfo();
    }
    public ActivityInfo renewActivityInfo(ActivityInfo cai) {
        return getProjectFromID(cai.getParentProjectId()).getActivityFromName(cai.getActivityName()).asInfo();
    }


    public ProjectInfo renewProjectInfo(ProjectInfo cpi) {
        return new ProjectInfo(getProjectFromID(cpi.getProjectID()));
    }

    public UserInfo renewUserInfo(UserInfo cui)  {
        try {
            return new UserInfo(getUserFromId(cui.getUserId()));
        } catch (UserIdDoesNotExistExeption e) {
            return new UserInfo(getCurrentUser());
        }
    }
    public UserInfo getCurrentUserInfo() {
        return this.currentUser.asInfo();
    }
    //Demo configuration -----------------------------------------------------------------------------------------------
    public void enableDemoConfig() throws UserIdAlreadyInUseExeption, UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException, InvalidDateException {

        String HUBA = registerUser("Hubert Baumeister").getUserId();
        String LOAN = registerUser("Lovro Antic").getUserId();
        String AAJ = registerUser("Asger Allin Jensen").getUserId();
        String NEL = registerUser("Niklas Emil Lysdal").getUserId();
        String NVT = registerUser("Nikolaj Vorndran Thygesen").getUserId();

        //Log in as Huba
        logInUser(HUBA);
        createProject("Soft. eng. project");
        Project p = getProjectFromTitle("Soft. eng. project");
        assert p != null;
        p.createNewActivity(new Activity("Design", 10,p.getProjectID()));
        p.createNewActivity(new Activity("Implementation", 20,p.getProjectID()));
        p.createNewActivity(new Activity("Testing", 10,p.getProjectID()));
        p.createNewActivity(new Activity("Documentation", 10,p.getProjectID()));
        p.assignUser(getUserFromId(LOAN));
        p.assignUser(getUserFromId(AAJ));
        p.assignUser(getUserFromId(NEL));
        p.assignUser(getUserFromId(NVT));
        p.setStartDate(new Calendar.Builder().setDate(2024, 3, 1).build());
        p.setDeadline(new Calendar.Builder().setDate(2024, 4, 6).build());

        Activity a = p.getActivityFromName("Design");
        a.assignUser(getUserFromId(NEL));
        a.assignUser(getUserFromId(NVT));
        a.logTime(5, getUserFromId(NEL));
        a.logTime(5, getUserFromId(NVT));
        a.setStatus(true);
        a.setStartdate(new Calendar.Builder().setDate(2024, 3, 1).build());
        a.setDeadline(new Calendar.Builder().setDate(2024, 4, 6).build());

        a = p.getActivityFromName("Implementation");
        a.assignUser(getUserFromId(LOAN));
        a.assignUser(getUserFromId(AAJ));
        a.logTime(10, getUserFromId(LOAN));
        a.logTime(2, getUserFromId(AAJ));
        a.setStatus(true);

        a = p.getActivityFromName("Testing");
        a.assignUser(getUserFromId(NEL));
        a.assignUser(getUserFromId(NVT));
        a.assignUser(getUserFromId(AAJ));
        a.assignUser(getUserFromId(LOAN));
        a.assignUser(getUserFromId(HUBA));
        a.logTime(5, getUserFromId(NEL));
        a.logTime(7, getUserFromId(NVT));
        a.logTime(10, getUserFromId(AAJ));
        a.logTime(9, getUserFromId(LOAN));
        a.logTime(5, getUserFromId(HUBA));
        a.setStartdate(new Calendar.Builder().setDate(2024, 4, 2).build());
        a.setDeadline(new Calendar.Builder().setDate(2024, 4, 6).build());

        a = p.getActivityFromName("Documentation");
        a.assignUser(getUserFromId(NEL));
        a.assignUser(getUserFromId(HUBA));
        a.logTime(5, getUserFromId(NEL));
        a.logTime(7, getUserFromId(HUBA));

        createProject("Yoga");
        p = getProjectFromTitle("Yoga");
        assert p != null;
        p.createNewActivity(new Activity("Yoga", 10,p.getProjectID()));
        p.createNewActivity(new Activity("Meditation", 20,p.getProjectID()));
        a = p.getActivityFromName("Yoga");
        a.logTime(5, getUserFromId(HUBA));


        logOut();

        //Log in as Asger
        logInUser(AAJ);
        createProject("Exam Preperation");
        p = getProjectFromTitle("Exam Preperation");
        assert p != null;
        p.createNewActivity(new Activity("Math 1b", 40,p.getProjectID()));
        p.createNewActivity(new Activity("Physics", 20,p.getProjectID()));
        p.createNewActivity(new Activity("Algorithms and datastructures", 30,p.getProjectID()));
        p.createNewActivity(new Activity("Software eng.", 70,p.getProjectID()));

        a = p.getActivityFromName("Math 1b");
        a.setStatus(true);

        logOut();

        //Log in as Lovro

        logInUser(LOAN);
        createProject("Coding");
        p = getProjectFromTitle("Coding");
        assert p != null;
        p.createNewActivity(new Activity("Java (•́︵•̀)", 40,p.getProjectID()));
        p.createNewActivity(new Activity("Python", 20,p.getProjectID()));
        p.createNewActivity(new Activity("C++", 30,p.getProjectID()));
        p.createNewActivity(new Activity("C#", 70,p.getProjectID()));

        logOut();

        //Log in as Niklas

        logInUser(NEL);
        createProject("Git gud");
        p = getProjectFromTitle("Git gud");
        assert p != null;
        p.createNewActivity(new Activity("Quick Scoping", 40,p.getProjectID()));
        p.createNewActivity(new Activity("No scoping", 20,p.getProjectID()));
        p.createNewActivity(new Activity("360 no scoping", 30,p.getProjectID()));

        a = p.getActivityFromName("No Scoping");
        a.setStatus(true);

        logOut();

        //Log in as Nikolaj
        logInUser(NVT);
        createProject("Rapport 2");
        p = getProjectFromTitle("Rapport 2");
        assert p != null;
        p.createNewActivity(new Activity("Rapport", 40,p.getProjectID()));
        p.createNewActivity(new Activity("Rapport 2", 20,p.getProjectID()));
        p.createNewActivity(new Activity("Rapport 2 electric boogaloo", 30,p.getProjectID()));
        p.createNewActivity(new Activity("Rapport 4", 70,p.getProjectID()));

        logOut();
    }


}