package presentation;

import app.ActivityInfo;
import app.App;
import app.ProjectInfo;
import app.UserInfo;
import domain.Interfaces.FinalUserCount;
import domain.exceptions.AUserIsAlreadyLoggedInException;
import domain.exceptions.InvalidDateException;
import domain.exceptions.UserIdAlreadyInUseExeption;
import domain.exceptions.UserIdDoesNotExistExeption;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;


public class Viewer { // Author Asger
    public static final App app = new App();

    private static UserInfo currentUserInfo = new UserInfo();

    private static ActivityInfo currentActivityInfo = new ActivityInfo();

    private static ProjectInfo currentProjectInfo = new ProjectInfo();
    public static void main(String[] args) throws UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException, UserIdAlreadyInUseExeption, InvalidDateException {
        // App setup
        Scanner programScanner = new Scanner(System.in);

        //----Program Loops------------------------------------------------------------------------------
        // Login & Register user Slice
        int loginValue = -1;
        while(true){
            switch (loginValue) {
                case 1:
                    System.out.println("Enter User id");
                    try {
                        loginUser(programScanner);
                    }catch (NullPointerException e){
                        System.out.println("Please enter a valid user id");
                    } catch (UserIdDoesNotExistExeption e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Enter name to create user id");
                    try {
                        createUser(programScanner);

                    } catch (UserIdAlreadyInUseExeption e){
                        System.out.println(e.getMessage());
                    }
                    break;

                default:
                    System.out.println("Enter:\n'1' to log in \n'2' to register a new user, \n'ids' to see all user ids,\n'Enable demo mode' to enable a default config\n'Exit' to exit the program");
            }

            String input = programScanner.nextLine();
            try {
                if(input.equalsIgnoreCase("ids") && !app.getUserList().isEmpty()){
                    System.out.println(app.getRegisteredUsers());
                }else if(input.equalsIgnoreCase("Exit")) {
                    programScanner.close();
                    break;
                }else if(input.equalsIgnoreCase("Enable demo mode")) {
                    app.enableDemoConfig();
                    System.out.println("Demo mode enabled");
                }
                loginValue = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                loginValue = -1;
            }
        }
    }


    private static void showMainMenu(Scanner scanner) {
        // Project overview and create project slice
        int startvalue = 0;
        while((app.loggedInStatus())){
            if (startvalue != 0){
                try{
                    currentProjectInfo = app.getCurrentUserProjectsInfoFromID(String.valueOf(startvalue));
                    insideProjectMenu(scanner);

                }catch (IndexOutOfBoundsException | NullPointerException e){
                    System.out.println("Project not found");
                    startvalue = 0;
                    continue;
                }
            } else {
                mainMenuOverview();
            }

            String input = scanner.nextLine();

            try {
                if (input.equalsIgnoreCase("NEW")){
                    newProject();
                }else if(input.equalsIgnoreCase("Exit")){
                    app.logOut();
                    break;
                } else if (input.equalsIgnoreCase("leave")) {
                    registerNewLeave(scanner);
                } else if (input.equalsIgnoreCase("remove leave")) { 
                    removeLeave(scanner);
                }
                startvalue = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                startvalue = 0;
            }
        }
        refreshUserInfo();
    }



    private static void insideProjectMenu(Scanner scanner) {
        int insideProjectValue = 0;
        while(true){
            if(insideProjectValue != 0){
                try{
                    currentActivityInfo = app.getActivityInfoFromIndex(currentProjectInfo, insideProjectValue-1);
                    enterActivity(scanner);
                }catch (IndexOutOfBoundsException e){
                    System.out.println("Activity not found");
                    continue;
                }
            } else {
                inProjectMenu();
            }
            String input = scanner.nextLine();
            try {
                if (input.equalsIgnoreCase("NEW")){
                    newActivityInProject();
                }else if(input.equalsIgnoreCase("Exit")){
                    mainMenuOverview();
                    break;
                }else if(input.equalsIgnoreCase("Add")){
                    addUserToProject(scanner);


                }else if(input.equalsIgnoreCase("Remove")) {
                    removeUserFromProject(scanner);
                } else if (input.equalsIgnoreCase("Completion")) {
                    changeProjectCompletion(scanner);

                } else if (input.equalsIgnoreCase("set start date")) {
                    setStartDateProject(scanner);

                } else if (input.equalsIgnoreCase("set deadline")) {
                    setDeadlineProject(scanner);
                }

                insideProjectValue = Integer.parseInt(input);
            } catch (NumberFormatException | InvalidDateException e) {
                insideProjectValue = 0;
            }
        }
        refreshProjectInfoObject();
    }


    private static void enterActivity(Scanner scanner) {
        int enterProjectValue = 0;
        while(true){
            if(enterProjectValue == 0){
                inActivityMenu();
            }
            String input = scanner.nextLine();
            try {
                if (input.equalsIgnoreCase("Log")) {
                    System.out.println("Enter hours worked today:");
                    String workedTimeString = scanner.nextLine();
                    try {
                        double workedTime = Double.parseDouble(workedTimeString);
                        app.logTimeOnActivity(workedTime, currentActivityInfo);
                    }catch (Exception e){
                        System.out.println("Invalid time input");
                    }
                }
                else if (input.equalsIgnoreCase("Completion")){
                    changeActivityCompletion(scanner);
                    inProjectMenu();
                    break;

                }else if(input.equalsIgnoreCase("Exit")){
                    inProjectMenu();
                    break;
                }else if(input.equalsIgnoreCase("Assign")){
                    assignUserActivity(scanner);

                }else if(input.equalsIgnoreCase("See Time Worked")){
                    System.out.println(currentActivityInfo.timeMapToString());
                }else if(input.equalsIgnoreCase("Remove")) {
                    removeUserActivity(scanner);

                } else if (input.equalsIgnoreCase("find free employee")) {
                    printFreeEmployees(app.findFreeEmployee(currentActivityInfo));
                } else if (input.equalsIgnoreCase("set start date")) {
                    setStartDateActivity(scanner);


                } else if (input.equalsIgnoreCase("set deadline")) {
                    setDeadlineActivity(scanner);
                }
                enterProjectValue = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                enterProjectValue = 0;
            }
            refreshActivityInfos();
        }
    }





    //----Login and register methods------------------------------------------------------------------
    private static void createUser(Scanner sc) throws UserIdAlreadyInUseExeption, UserIdDoesNotExistExeption {
        String userId = (sc.nextLine());
        app.registerUser(userId);
        String newId = app.createUserId(userId);
        System.out.println("Your user id is: "+ app.getActualId(newId));
        System.out.println("Please remember this id, press the 'Enter' button to continue");
    }

    private static void loginUser(Scanner sc) throws UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException {
        String loginString = sc.next();
        app.logInUser(loginString);
        currentUserInfo = app.getCurrentUserInfo();
        showMainMenu(sc);
    }

    //----Leave methods------------------------------------------------------------------------------
    private static void registerNewLeave(Scanner programScanner) {
        System.out.println("Please enter name of leave");
        String name = programScanner.nextLine();
        Calendar[] set = getDateSetFromUser(programScanner);
        app.registerLeave(name,set[0],set[1]);
    }

    private static void removeLeave(Scanner programScanner) {
        System.out.println("Please enter name of leave");
        String name = programScanner.nextLine();
        Calendar[] set = getDateSetFromUser(programScanner);
        app.removeLeave(name,set[0],set[1]);
    }


    //----Create events------------------------------------------------------------------------------
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

    //----Set dates-----------------------------------------------------------------------------------
    private static void setDeadlineProject(Scanner sc) throws InvalidDateException {
        boolean success = false;
        while (!success) {
            try {
                Calendar c = getWeekOfYearFromUser(sc);
                c.set(Calendar.DAY_OF_WEEK, 8); //sunday. Java.Calendar has weird day of week format.
                app.setProjectDeadline(c, currentProjectInfo);
                refreshProjectInfoObject();
                success = true;
            } catch (InvalidDateException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again.-");
            }
        }
    }

    private static void setStartDateProject(Scanner sc) throws InvalidDateException {
        boolean success = false;
        while (!success) {
            try {
                Calendar c = getWeekOfYearFromUser(sc);
                app.setProjectStartDate(c, currentProjectInfo);
                refreshProjectInfoObject();
                success = true;

            } catch (InvalidDateException e) {
                System.out.print(e.getMessage());
                System.out.println("please try again;");
            }
        }

    }

    private static void setDeadlineActivity(Scanner scanner) {
        boolean success = false;
        while (!success) {
            try {
                Calendar c = getWeekOfYearFromUser(scanner);
                app.setActivityDeadlineFromInfo(currentActivityInfo,c);
                success=true;
            } catch (InvalidDateException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }

        }
    }

    private static void setStartDateActivity(Scanner scanner) {
        boolean success =false;
        while(!success) {
            try {
                Calendar c = getWeekOfYearFromUser(scanner);
                app.setActivityStartDateFromInfo(currentActivityInfo, c);
                success = true;
            } catch(InvalidDateException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
    }

    //----Manage Participants-------------------------------------------------------------------------

    private static void removeUserActivity(Scanner scanner) {
        System.out.println("Enter user id of user to be removed from activity");
        String removeName = scanner.nextLine();
        try{
            app.removeUserFromActivity(removeName, currentActivityInfo);
        }catch(UserIdDoesNotExistExeption e) {
            System.out.println(e.getMessage());
        }
    }

    private static void assignUserActivity(Scanner activityScanner) {
        System.out.println("Enter user id of user to be added to activity");
        String addName = activityScanner.nextLine();
        try {
            app.assignUserToActivity(addName, currentActivityInfo);
        } catch (UserIdDoesNotExistExeption e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addUserToProject(Scanner scanner) {
        System.out.println("Enter user id of user to be added to project");
        String addName = scanner.nextLine();
        try{
            app.assignUserToProject(addName, currentProjectInfo);
        } catch (UserIdDoesNotExistExeption e) {
            System.out.println(e.getMessage());
        }
    }

    private static void removeUserFromProject(Scanner scanner) {
        System.out.println("Enter user id of user to be removed from the project");
        String removeName = scanner.nextLine();

        try {
            app.removeUserFromProject(removeName, currentProjectInfo);
        } catch (UserIdDoesNotExistExeption e) {
            System.out.println(e.getMessage());
        }
    }

    //Print methods---------------------------------------------------------------------------------
    private static void mainMenuOverview(){
        System.out.println();
        System.out.println("____________________________________________________________________________________________________________________");
        System.out.println("Logged in with user Id: "+ app.getCurrentUserId());
        System.out.println("List of projects:");
        System.out.println(app.getProjectListString());
        System.out.println(app.getCurrentUserActivityListString());
        System.out.println("Enter: \nthe project id to enter a project, \n\"NEW\" to make a new project, \n\"LEAVE\" to register leave, \n\"REMOVE LEAVE\" to remove leave or \n\"Exit\" to exit app");

    }

    private static void inProjectMenu(){
        System.out.println();
        System.out.println("____________________________________________________________________________________________________________________");
        System.out.println("Project: "+ currentProjectInfo.getName());
        System.out.println("Project Leader: "+ currentProjectInfo.getProjectLeader());
        System.out.println("Project status: " + (currentProjectInfo.getComplete()? "Complete" :"Incomplete"));
        System.out.println("Project Members: "+ currentProjectInfo.getParticipanList());
        System.out.println(currentProjectInfo.completionPercentageString());
        System.out.println("List of Activities:");
        System.out.println("Activities: " +app.getActivityListString(currentProjectInfo));
        System.out.println("Startdate: " + currentProjectInfo.getStartDate());
        System.out.println("Deadline: " + currentProjectInfo.getDeadline());
        System.out.println();
        System.out.println("Enter: \nthe number for the activity,\n\"ADD\" to add member to project,\n\"Remove\" to remove a member from the project, \n\"NEW\" to make a new activity, \n\"Completion\" to change completion status, \n\"Exit\" to go to main menu,");
        System.out.println("\"Set start date\" to set start date of project");
        System.out.println("or \"Set deadline\" to set deadline of project");
    }

    private static void inActivityMenu() {
        System.out.println();
        System.out.println("____________________________________________________________________________________________________________________");
        System.out.println("Activity name: " + currentActivityInfo.getActivityName());
        System.out.println("Activity status: " + (currentActivityInfo.isComplete()? "Complete" :"Incomplete"));
        System.out.println("Overdue:" + ((currentActivityInfo.isComplete() ? "No" :(app.isActivityOverdue(currentActivityInfo)?"Yes":"No"))));
        System.out.println("Activity Members: " + currentActivityInfo.getParticipantList());
        System.out.println("Budgeted time: " + currentActivityInfo.getBudgetTime());
        System.out.println("Overbudget:"+app.isActivityOverBudget(currentActivityInfo));
        System.out.println("Activity start date: " + (currentActivityInfo.getStartDate()));
        System.out.println("Activity deadline: " + (currentActivityInfo.getDeadline()));
        System.out.println();
        System.out.println("Enter: \n \"Set start date\" to set start date.");
        System.out.println("\"Set deadline\" to set deadline.");
        System.out.println("\"Log\" to log worked time, \n\"See time worked\" to see time worked on project,\n\"Completion\" to complete activity,\n\"Assign\" to assign user to activity, \n\"Remove\" to remove a user from the activity \n\"Exit\" to go to main menu");
        if (!currentActivityInfo.getDeadline().equals("Date not set.") && !currentActivityInfo.getStartDate().equals("Date not set.")) {
            System.out.println("or \" Find free employee\" to find free employee in project.");
        }

    }

    private static void printFreeEmployees(ArrayList<FinalUserCount> results) {
        StringBuilder resultsString = new StringBuilder();
        String output;
        if (results.isEmpty()) {
            resultsString.append("No employees available.");
            output=resultsString.toString();
        } else {
            for (FinalUserCount u : results) {
                resultsString.append(u.getUserID()).append(": ").append(u.getCount()).append(" activities overlapping").append(",");
            }
            output=resultsString.substring(0,resultsString.length()-1)+".";
        }
        System.out.println(output);
    }

    //--------Get from user methods----------------------------------------------------------------
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


    private static Calendar[] getDateSetFromUser (Scanner input) {
        Calendar[] set  = new Calendar[2];
        boolean validSet = false;
        Calendar start=null;
        Calendar end=null;

        while(!validSet) {
            System.out.println("First start date:");
            start = getDateFromUser(input);
            System.out.println("Now end date:");
            end = getDateFromUser(input);
            if (end.after(start)) {
                validSet=true;
            } else {
                System.out.println("End before start, please try again");
            }
        }
        set[0]=start;
        set[1]=end;
        return set;
    }
    private static Calendar getDateFromUser(Scanner input) {

        Calendar c = new GregorianCalendar();
        boolean success = false;
        int year=0;
        while (!success) {
            System.out.println("Enter year (yyyy):");
            try {
                year = input.nextInt();
                success=true;
            } catch(Exception e) {
                System.out.println("Invalid year, please try again.");
            }
        }
        c.set(Calendar.YEAR,year);
        success=false;
        int month=0;
        while (!success) {
            System.out.println("Enter month (mm):");
            try {
                month = input.nextInt();
                if (month>0 && month<=12) {
                    success=true;
                } else {
                    System.out.println("Please enter valid month.");
                }
            } catch(Exception e) {
                System.out.println("Invalid month, please try again.");
            }
        }
        month--; //month-1 0-indeksering
        c.set(Calendar.MONTH,month);
        int day = 0;
        success=false;
        while (!success) {
            System.out.println("Enter day (dd):");
            try {
                day = input.nextInt();
                if (day>0 && day<=c.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    success=true;
                } else {
                    System.out.println("Please enter valid day.");
                }
            } catch(Exception e) {
                System.out.println("Invalid day, please try again.");
            }
        }
        c.set(Calendar.DAY_OF_MONTH,day);
        return c;






    }

    //----Function methods --------------------------------------------
    private static void changeProjectCompletion(Scanner sc) {
        System.out.println("is project complete?(Y/N)");
        boolean success =false;
        String input ="";
        while(!success) {
            input = sc.nextLine();
            if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
                success=true;
            }
        }

        boolean newValue= input.equalsIgnoreCase("Y");
        app.changeCompletenessOfProject(newValue,currentProjectInfo);
        currentProjectInfo=app.renewProjectInfo(currentProjectInfo);
    }

    private static void changeActivityCompletion(Scanner sc) {
        System.out.println("is activity complete?(Y/N)");
        boolean success =false;
        String input ="";
        while(!success) {
            input = sc.nextLine();
            if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
                success=true;
            }
        }
        boolean newValue= input.equalsIgnoreCase("Y");
        app.ChangeCompletenessOfActivity(newValue,currentActivityInfo);
        currentActivityInfo=app.renewActivityInfo(currentActivityInfo);
    }


    //----- Refresh infos--------------------------------------------------------------------------------
    private static void refreshProjectInfoObject(){
        currentProjectInfo = app.renewProjectInfo(currentProjectInfo);
    }

    private static void refreshActivityInfos() {
        currentActivityInfo = app.renewActivityInfo(currentActivityInfo);
    }


    private static void refreshUserInfo() {
        currentUserInfo = app.renewUserInfo(currentUserInfo);
    }

}
