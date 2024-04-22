package presentation;

import domain.AUserIsAlreadyLoggedInException;
import app.App;
import domain.UserIdAlreadyInUseExeption;
import domain.UserIdDoesNotExistExeption;
import domain.*;

import java.util.Scanner;

public class Viewer { // Author Asger
    public static final App app = new App();
    public static void main(String[] args) throws UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException, UserIdAlreadyInUseExeption {
        // App setup

        Scanner loginScanner = new Scanner(System.in);

        // Login & Register user Slice
        int loginValue = -1;
        while(true){
            if (loginValue == 1){
                System.out.println("Enter User id");
                try {
                    app.logInUser(loginScanner.next().substring(0, 4).toUpperCase());
                    showMainMenu();
                }catch (StringIndexOutOfBoundsException e){
                    System.out.println("User Id has 4 characters");
                } catch (UserIdDoesNotExistExeption e){
                    System.out.println("User id does not exist");
                }

            }else if (loginValue == 2){
                System.out.println("Enter name to create user id");
                try {
                    User outuser = app.registerUser(loginScanner.next());
                    System.out.println("Your user id is: "+ outuser.getUserId());
                } catch (UserIdAlreadyInUseExeption e){
                    System.out.println("User id already exists");
                }

            }else{
                System.out.println("Enter '1' to log in, or '2' to register a new user ");
            }
            String input = loginScanner.nextLine();
            try {
                loginValue = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                loginValue = -1;
            }
        }
    }

    private static void showMainMenu() {
        // Project overview and create project slice
        int startvalue = 0;
        Scanner programScanner = new Scanner(System.in);
        while((app.getCurrentUser() != null)){
            if (startvalue > 0){
                try{
                Project currentproject = app.getCurrentUser().getAssignedProjects().get(startvalue-1);
                enterProject(currentproject, currentproject);
            }catch (IndexOutOfBoundsException e){
                System.out.println("Project not found");
            }

            }else if(startvalue == 0){
                mainMenuOverview();
            }

            String input = programScanner.nextLine();

            try {
                if (input.equalsIgnoreCase("NEW")){
                    newProject();
                }else if(input.equalsIgnoreCase("Exit")){
                    app.logOut();
                    break;
                }
                startvalue = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                startvalue = 0;
            }
        }
    }

    private static void enterProject(Project project, Project currentProject) {
        Scanner projectScanner = new Scanner(System.in);
        int enterProjectValue = 0;
        while(true){
            if(enterProjectValue > 0){
                try{
                    Activity currentActivity = project.getActivityList().get(enterProjectValue-1);
                    enterActicvity(currentActivity, currentProject);
                }catch (IndexOutOfBoundsException e){
                    System.out.println("Activity not found");
                }


            }else if(enterProjectValue == 0){
                inProjectMenu(project);
            }
            String input = projectScanner.nextLine();
            try {
                if (input.equalsIgnoreCase("NEW")){
                    newActivityInProject(project);
                }else if(input.equalsIgnoreCase("Exit")){
                    mainMenuOverview();
                    break;
                }else if(input.equalsIgnoreCase("Add")){
                    System.out.println("Enter user id of user to be added to project");
                    String addName = projectScanner.nextLine();
                    try {
                        project.assignUser(app.getUserFromId(addName));
                    }catch (UserIdDoesNotExistExeption e){
                        System.out.println("User not found");
                    }
                }
                enterProjectValue = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                enterProjectValue = 0;
            }
        }
    }



    private static void enterActicvity(Activity activity, Project currentProject) {
        Scanner activityScanner = new Scanner(System.in);
        int enterProjectValue = 0;
        while(true){
            if(enterProjectValue == 0){
                inActivityMenu(activity);
            }
            String input = activityScanner.nextLine();
            try {
                if (input.equalsIgnoreCase("Complete")){
                    activity.setStatus(true);
                }else if(input.equalsIgnoreCase("Exit")){
                    inProjectMenu(currentProject);
                    break;
                }
                enterProjectValue = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                enterProjectValue = 0;
            }
        }
    }



    private static void newProject() {
        Scanner newProjectScanner = new Scanner(System.in);
        System.out.println("Enter Name of project: ");
        String name = newProjectScanner.nextLine();
        app.createProject(name);
    }

    private static void newActivityInProject(Project project){
        Scanner newActivityScanner = new Scanner(System.in);
        System.out.print("Enter activity name: " );
        String newActivityName = newActivityScanner.nextLine();
        int numberIn = 0;
        while (true) {
            if (numberIn > 0) {
                break;
            }else{
                System.out.print("Enter amount of budgeted hours: ");
            }
            String numberInput = newActivityScanner.nextLine();
            try {
                numberIn = Integer.parseInt(numberInput);
            } catch (NumberFormatException e) {
                numberIn = 0;
            }
        }
        project.createNewActivity(new Activity(newActivityName, numberIn));
    }

    private static void mainMenuOverview(){
        System.out.println("Logged in with user Id: "+ app.getCurrentUserId());
        System.out.println("List of projects:");
        int i = 1;
        for(Project project : app.getCurrentUser().getAssignedProjects()){
            System.out.println(i + ": " +project.getProjectID() + " Name: " + project.getTitle());
            i++;
        }
        System.out.println("Enter the number for the project, \"NEW\" to make a new project, or \"Exit\" to exit app");
    }

    private static void inProjectMenu(Project project){
        System.out.println("Project: "+project.getTitle());
        if(project.getActivityList()!=null){
            System.out.println(project.completionPercentage());
        }
        StringBuilder nameString = new StringBuilder();
        for(int i = 0; i < project.getParticipantList().size(); i++){
            nameString.append(project.getParticipantList().get(i).getUserId()).append(", ");
        }
        System.out.println("Assigned users: " + nameString);

        System.out.println("List of Activities:");
        int i = 1;
        for(Activity activity : project.getActivityList()){
            System.out.println(i + ": " +activity.getName() + " Status: " + activity.getStatus());
            i++;
        }
        System.out.println("Enter the number for the activity,\"ADD\" to add member to project \"NEW\" to make a new activity,\nor \"Exit\" to go to main menu");
    }

    private static void inActivityMenu(Activity activity) {
        System.out.println("Activity name: " + activity.getName());
        System.out.println("Activity status: " + (activity.getStatus()? "Complete" :"Incomplete"));
        System.out.println("Activity Members: " + activity.getParticipanList());
        System.out.println("Enter \"Complete\" to complete activity, or \"Exit\" to go to main menu");
    }

    private static void clearScreen(){
        for(int i = 0; i < 20; i++){
            System.out.println("");
        }
    }
}
