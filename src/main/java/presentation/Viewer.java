package presentation;

import app.ActivityInfo;
import app.ProjectInfo;
import app.UserInfo;
import domain.AUserIsAlreadyLoggedInException;
import app.App;
import domain.UserIdAlreadyInUseExeption;
import domain.UserIdDoesNotExistExeption;
import io.cucumber.messages.types.Exception;
import domain.*;
import javafx.geometry.Dimension2D;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;


public class Viewer { // Author Asger
    public static final App app = new App();

    private static UserInfo currentuser = new UserInfo();

    private static ActivityInfo currentActivity = new ActivityInfo();

    private static ProjectInfo currentProject = new ProjectInfo();
    public static void main(String[] args) throws UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException, UserIdAlreadyInUseExeption {
        // App setup

        Scanner loginScanner = new Scanner(System.in);

        // Login & Register user Slice
        int loginValue = -1;
        while(true){
            if (loginValue == 1){
                System.out.println("Enter User id");
                try {
                    String loginString = loginScanner.next().substring(0, 4).toUpperCase();
                    app.logInUser(loginString);
                    currentuser = new UserInfo(app.getCurrentUser());
                    showMainMenu();
                }catch (StringIndexOutOfBoundsException e){
                    System.out.println("User Id has 4 characters");
                } catch (UserIdDoesNotExistExeption e){
                    System.out.println(e.getMessage());
                }

            }else if (loginValue == 2){
                System.out.println("Enter name to create user id");
                try {
                    String userId = (loginScanner.next());
                    app.registerUser(userId);
                    System.out.println("Your user id is: "+ app.getActualId(userId));
                } catch (UserIdAlreadyInUseExeption e){
                    System.out.println(e.getMessage());
                }

            }else{
                System.out.println("Enter '1' to log in, or '2' to register a new user, or 'ids' to see all user ids");
            }
            String input = loginScanner.nextLine();
            try {
                if(input.equalsIgnoreCase("ids") && !app.getUserList().isEmpty()){
                    System.out.println(app.getRegisteredUsers());
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
            if (startvalue > 0 && startvalue <= currentuser.getAssignedProjects().size()){
                try{
                    currentProject = new ProjectInfo(app.getProjectFromIndex(startvalue-1));
                    insideProjectMenu();

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

    private static void insideProjectMenu() {
        Scanner projectScanner = new Scanner(System.in);
        int insideProjectValue = 0;
        while(true){
            if(insideProjectValue > 0){
                try{
                    currentActivity = new ActivityInfo(app.getActivityFromIndex(currentProject, insideProjectValue-1));
                    currentActivity.setParentProjectID(currentProject.getProjectID());
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
                    app.assignUserToProject(addName, currentProject);
                } else if (input.equalsIgnoreCase("set start date")) {
                    boolean success = false;
                    while (!success) {
                        try {
                            Calendar c = getWeekOfYearFromUser(projectScanner);
                            app.setProjectStartDate(c, currentProject);
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
                        app.setProjectDeadline(c, currentProject);
                        success=true;
                    }
                }
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
                        app.logTimeOnActivity(workedTime, app.getCurrentUser(), currentActivity);
                    }catch (java.lang.Exception e){
                        System.out.println("Invalid time input");
                    }
                }
                else if (input.equalsIgnoreCase("Complete")){
                    app.ChangeCompletenessOfActivity(true, currentActivity);
                    inProjectMenu();
                    break;
                }else if(input.equalsIgnoreCase("Exit")){
                    inProjectMenu();
                    break;
                }else if(input.equalsIgnoreCase("Assign")){
                    System.out.println("Enter user id of user to be added to activity");
                    String addName = activityScanner.nextLine();
                    app.assignUserToActivity(addName, currentActivity);
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
        while (true) {
            if (numberIn > 0) {
                break;
            }else{
                System.out.print("Enter amount of budgeted hours: ");
            }
            String numberInput = newActivityScanner.nextLine();
            try {
                numberIn = Double.parseDouble(numberInput);
            } catch (NumberFormatException e) {
                numberIn = 0;
            }
        }
        app.createNewActivity(newActivityName, numberIn, currentProject);
    }

    private static void mainMenuOverview(){
        System.out.println("Logged in with user Id: "+ app.getCurrentUserId());
        System.out.println("List of projects:");
        System.out.println(app.getProjectListString());
        System.out.println("Enter the number for the project, \"NEW\" to make a new project, or \"Exit\" to exit app");
    }

    private static void inProjectMenu(){
        System.out.println("Project: "+currentProject.getName());
        System.out.println("Project Leader: "+currentProject.getProjectLeader());
        System.out.println("Project Members: "+currentProject.getParticipanList());
        System.out.println(app.getProjectCompletionString(currentProject));
        System.out.println("List of Activities:");
        System.out.println(app.getActivityListString(currentProject));
        System.out.println("Startdate: " + dateToString(currentProject.getStartDate()));
        System.out.println("Deadline: " + dateToString(currentProject.getDeadline()));
        System.out.println("Enter the number for the activity,\"ADD\" to add member to project \"NEW\" to make a new activity,\nor \"Exit\" to go to main menu");
        System.out.println("Enter \"Set start date\" to set start date of project:");
        System.out.println("Enter \"Set deadline\" to set deadline of project:");
    }

    private static void inActivityMenu() {
        System.out.println("Activity name: " + currentActivity.getActivityName());
        System.out.println("Activity status: " + (currentActivity.getIsComplete()? "Complete" :"Incomplete"));
        System.out.println("Activity Members: " + currentActivity.getParticipantList());
        System.out.println("Enter \"Log\" to log worked time, \"Complete\" to complete activity,\n\"Assign\" to assign user to activity or \"Exit\" to go to main menu");
    }


    private static Calendar stringToDate(String input) throws InvalidDateFormatException {
        // format input to dates
        if (!input.contains("/")) {
            throw new InvalidDateFormatException("doesnt contain");
        }
        String[] dmy = input.split("/");

        int day;
        int month;
        int year;
        try {
            day = Integer.parseInt(dmy[0]);
            month = Integer.parseInt(dmy[1]) - 1;
            year = Integer.parseInt(dmy[2]);
        } catch (java.lang.Exception e) {
            throw new InvalidDateFormatException("invalid date format. Please use dd/mm/yyyy");
        }
        if (day < 1) {
            throw new InvalidDateFormatException("Invalid day");
        }
        if (month > 11 || month < 0) {
            throw new InvalidDateFormatException("Month must be a number between 1 and 12");
        }
        int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (month == 1) { // february for leap year
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                if (day > 29) {
                    throw new InvalidDateFormatException("Invalid day of month");
                } else if (day > 28) {
                    throw new InvalidDateFormatException("Invalid day of month");
                }
            }
        } else {
            if (day > daysInMonth[month]) {
                throw new InvalidDateFormatException("Invalid day of month");
            }
        }
        return new GregorianCalendar(year, month, day);
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
}
