package presentation;

import app.ActivityInfo;
import app.App;
import app.ProjectInfo;
import app.UserInfo;
import domain.AUserIsAlreadyLoggedInException;
import domain.InvalidDateException;
import domain.UserIdAlreadyInUseExeption;
import domain.UserIdDoesNotExistExeption;
import java.util.Calendar;
import java.util.Scanner;


public class Viewer { // Author Asger
    public static final App app = new App();

    private static UserInfo currentUserInfo = new UserInfo();

    private static ActivityInfo currentActivityInfo = new ActivityInfo();

    private static ProjectInfo currentProjectInfo = new ProjectInfo();
    public static void main(String[] args) throws UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException, UserIdAlreadyInUseExeption {
        // App setup

        Scanner loginScanner = new Scanner(System.in);

        // Login & Register user Slice
        int loginValue = -1;
        while(true){
            switch (loginValue) {
                case 1:
                    System.out.println("Enter User id");
                    try {
                        String loginString = loginScanner.next().substring(0, 4).toUpperCase();
                        app.logInUser(loginString);
                        currentUserInfo = new UserInfo(app.getCurrentUser());
                        showMainMenu();
                    }catch (StringIndexOutOfBoundsException e){
                        System.out.println("User Id has 4 characters");
                    } catch (UserIdDoesNotExistExeption e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Enter name to create user id");
                    try {
                        String userId = (loginScanner.next());
                        app.registerUser(userId);
                        System.out.println("Your user id is: "+ app.getActualId(userId));
                    } catch (UserIdAlreadyInUseExeption e){
                        System.out.println(e.getMessage());
                    }
                    break;

                default:
                    System.out.println("Enter '1' to log in, or '2' to register a new user, 'ids' to see all user ids\nor 'Exit' to exit the program");
            }

            String input = loginScanner.nextLine();
            try {
                if(input.equalsIgnoreCase("ids") && !app.getUserList().isEmpty()){
                    System.out.println(app.getRegisteredUsers());
                }else if(input.equalsIgnoreCase("Exit")) {
                    break;
                }
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
        while((app.loggedInStatus())){
            if (startvalue > 0){
                try{
                    currentProjectInfo = new ProjectInfo(app.getProjectFromIndex(startvalue-1));
                    insideProjectMenu(startvalue-1);

                }catch (IndexOutOfBoundsException | NullPointerException e){
                    System.out.println("Project not found");
                    startvalue = 0;
                }
            }
            if(startvalue == 0){
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

    private static void insideProjectMenu(int value) {
        Scanner projectScanner = new Scanner(System.in);
        int insideProjectValue = 0;
        while(true){
            if(insideProjectValue > 0){
                try{
                    currentActivityInfo = new ActivityInfo(app.getActivityFromIndex(currentProjectInfo, insideProjectValue-1));
                    currentActivityInfo.setParentProjectID(currentProjectInfo.getProjectID());
                    enterActicvity();
                }catch (IndexOutOfBoundsException e){
                    System.out.println("Activity not found");
                }


            }else if(insideProjectValue == 0){
                inProjectMenu();
            }
            String input = projectScanner.nextLine();
            try {
                if (input.equalsIgnoreCase("NEW")){
                    newActivityInProject();
                }else if(input.equalsIgnoreCase("Exit")){
                    mainMenuOverview();
                    break;
                }else if(input.equalsIgnoreCase("Add")){
                    System.out.println("Enter user id of user to be added to project");
                    String addName = projectScanner.nextLine();
                    app.assignUserToProject(addName, currentProjectInfo);
                } else if (input.equalsIgnoreCase("set start date")) {
                    boolean success = false;
                    while (!success) {
                        try {
                            Calendar c = getWeekOfYearFromUser(projectScanner);
                            app.setProjectStartDate(c, currentProjectInfo);
                            success=true;
                        } catch (InvalidDateException e) {
                            System.out.print(e.getMessage());
                            System.out.println("please try again;");
                        }
                    }

                } else if (input.equalsIgnoreCase("set deadline")) {
                    boolean success = false;
                    while (!success) {
                        Calendar c = getWeekOfYearFromUser(projectScanner);
                        c.set(Calendar.DAY_OF_WEEK,8); //sunday. Java.Calendar has weird day of week format.
                        app.setProjectDeadline(c, currentProjectInfo);
                        success=true;
                    }
                }
                refreshProjectInfoObject(value);
                insideProjectValue = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                insideProjectValue = 0;
            }
        }
    }



    private static void enterActicvity() {
        Scanner activityScanner = new Scanner(System.in);
        int enterProjectValue = 0;
        while(true){
            if(enterProjectValue == 0){
                inActivityMenu();
            }
            String input = activityScanner.nextLine();
            try {
                if (input.equalsIgnoreCase("Log")) {
                    System.out.println("Enter hours worked today:");
                    String workedTimeString = activityScanner.nextLine();
                    try {
                        double workedTime = Double.parseDouble(workedTimeString);
                        app.logTimeOnActivity(workedTime, app.getCurrentUser(), currentActivityInfo);
                    }catch (java.lang.Exception e){
                        System.out.println("Invalid time input");
                    }
                }
                else if (input.equalsIgnoreCase("Complete")){
                    app.ChangeCompletenessOfActivity(true, currentActivityInfo);
                    inProjectMenu();
                    break;
                }else if(input.equalsIgnoreCase("Exit")){
                    inProjectMenu();
                    break;
                }else if(input.equalsIgnoreCase("Assign")){
                    System.out.println("Enter user id of user to be added to activity");
                    String addName = activityScanner.nextLine();
                    app.assignUserToActivity(addName, currentActivityInfo);
                }else if(input.equalsIgnoreCase("See Time Worked")){
                    System.out.println(app.timeMapToString(currentActivityInfo));
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

    private static void newActivityInProject(){
        Scanner newActivityScanner = new Scanner(System.in);
        System.out.print("Enter activity name: " );
        String newActivityName = newActivityScanner.nextLine();
        double numberIn = 0;
        while (!(numberIn > 0)) {
            System.out.print("Enter amount of budgeted hours: ");
            String numberInput = newActivityScanner.nextLine();
            try {
                numberIn = Double.parseDouble(numberInput);
            } catch (NumberFormatException e) {
                numberIn = 0;
            }
        }
        app.createNewActivity(newActivityName, numberIn, currentProjectInfo);
    }

    private static void mainMenuOverview(){
        System.out.println("Logged in with user Id: "+ app.getCurrentUserId());
        System.out.println("List of projects:");
        System.out.println(app.getProjectListString());
        System.out.println("Enter the number for the project, \"NEW\" to make a new project, or \"Exit\" to exit app");
    }

    private static void inProjectMenu(){
        System.out.println("Project: "+ currentProjectInfo.getName());
        System.out.println("Project Leader: "+ currentProjectInfo.getProjectLeader());
        System.out.println("Project status: " + (currentProjectInfo.getComplete()? "Complete" :"Incomplete"));
        System.out.println("Project Members: "+ currentProjectInfo.getParticipanList());
        System.out.println(app.getProjectCompletionString(currentProjectInfo));
        System.out.println("List of Activities:");
        System.out.println(app.getActivityListString(currentProjectInfo));
        System.out.println("Startdate: " + dateToString(currentProjectInfo.getStartDate()));
        System.out.println("Deadline: " + dateToString(currentProjectInfo.getDeadline()));
        System.out.println("Enter the number for the activity,\"ADD\" to add member to project \"NEW\" to make a new activity,\nor \"Exit\" to go to main menu");
        System.out.println("Enter \"Set start date\" to set start date of project:");
        System.out.println("Enter \"Set deadline\" to set deadline of project:");
    }

    private static void inActivityMenu() {
        System.out.println("Activity name: " + currentActivityInfo.getActivityName());
        System.out.println("Activity status: " + (currentActivityInfo.getIsComplete()? "Complete" :"Incomplete"));
        System.out.println("Activity Members: " + currentActivityInfo.getParticipantList());
        System.out.println("Enter \"Log\" to log worked time, \"See worked time\" to see time worked on project,\n\"Complete\" to complete activity,\"Assign\" to assign user to activity or \"Exit\" to go to main menu");
    }



    private static Calendar getWeekOfYearFromUser (Scanner input) {
        System.out.println("What year? (format: yyyy)");
        boolean successfullYear = false;
        int year=1;
        Calendar c= Calendar.getInstance();
        c.clear();
        c.set(Calendar.YEAR,year);
        while (!successfullYear) {
            try {
                year = Integer.parseInt(input.nextLine());
                successfullYear=true;

            } catch (java.lang.Exception e){
                System.out.println("Invalid year. Please try again");
            }
        }
        c.set(Calendar.YEAR,year);
        System.out.println("What week? (format: ww)");
        boolean successfullWeek=false;
        int week=1;
        while (!successfullWeek) {
            try {
                week= Integer.parseInt(input.nextLine());
                if (week >c.getActualMaximum(Calendar.WEEK_OF_YEAR) || week <1) {
                    throw new InvalidDateException("");
                }
                successfullWeek=true;

            } catch (java.lang.Exception e){
                System.out.println("Invalid week. Please try again.");
            }
        }

        c.set(Calendar.WEEK_OF_YEAR,week);

        return c;

    }
    private static String dateToString(Calendar d) {
        if (d==null) {
            return "Date not Set";
        } else {
            return d.get(Calendar.DAY_OF_MONTH)+"/"+(d.get(Calendar.MONTH)+1)+"/"+d.get(Calendar.YEAR) +"(Week: "+d.get(Calendar.WEEK_OF_YEAR)+")";
        }
    }


    private static void refreshProjectInfoObject(int value){
        currentProjectInfo = new ProjectInfo(app.getProjectFromIndex(value));
    }

}
