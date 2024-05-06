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
/*
@ Author: Asger Allin Jensen
 */

public class Viewer {
    public static final App app = new App();

    public static final Scanner programScanner = new Scanner(System.in);
    private static UserInfo currentUserInfo = new UserInfo();

    private static ActivityInfo currentActivityInfo = new ActivityInfo();

    private static ProjectInfo currentProjectInfo = new ProjectInfo();

    // Author Asger Allin Jensen
    public static void main(String[] args) throws UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException, UserIdAlreadyInUseExeption, InvalidDateException {
        // App setup

        //----Program Loops------------------------------------------------------------------------------
        // Login & Register user Slice

        int loginValue = -1;
        while(true){
            switch (loginValue) {
                case 1:
                    System.out.println("Enter User id");
                    try {
                        loginUser();
                        coreMenu();
                    }catch (NullPointerException e){
                        System.out.println("Please enter a valid user id");
                    } catch (UserIdDoesNotExistExeption e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Enter name to create user id");
                    try {
                        createUser();
                        coreMenu();
                    } catch (UserIdAlreadyInUseExeption e){
                        System.out.println(e.getMessage());
                    }
                    break;

                default:
                    coreMenu();
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


    private static void showMainMenu() { // Author: Asger Allin Jensen
        // Project overview and create project slice
        if(programScanner.hasNextLine()){
            programScanner.nextLine();
        }
        int startvalue = 0;
        while((app.loggedInStatus())){

            if (startvalue != 0){
                try{
                    currentProjectInfo = app.getCurrentUserProjectsInfoFromID(String.valueOf(startvalue));
                    insideProjectMenu();
                    showUserMenu();
                }catch (IndexOutOfBoundsException | NullPointerException e){
                    System.out.println("Project not found");
                    startvalue = 0;
                    continue;
                }
            } else {
                showUserMenu();
            }

            String input = programScanner.nextLine();

            try {
                if (input.equalsIgnoreCase("NEW")){
                    newProject();
                }else if(input.equalsIgnoreCase("Exit")){
                    app.logOut();
                    break;
                } else if (input.equalsIgnoreCase("leave")) {
                    registerNewLeave();
                } else if (input.equalsIgnoreCase("remove leave")) {
                    removeLeave();
                }
                startvalue = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                startvalue = 0;
            }
        }
        refreshUserInfo();
    }





    private static void insideProjectMenu() { // Author: Asger Allin Jensen

        int insideProjectValue = 0;
        while(true){
            if(insideProjectValue != 0){
                try{
                    currentActivityInfo = app.getActivityInfoFromIndex(currentProjectInfo, insideProjectValue-1);
                    enterActivity();
                }catch (IndexOutOfBoundsException e){
                    System.out.println("Activity not found");
                    continue;
                }
            } else {
                showProjectMenu();
            }
            String input = programScanner.nextLine();
            try {
                if (input.equalsIgnoreCase("NEW")){
                    newActivityInProject();
                }else if(input.equalsIgnoreCase("Exit")){
                    break;
                }else if(input.equalsIgnoreCase("Add")){
                    addUserToProject();


                }else if(input.equalsIgnoreCase("Remove")) {
                    removeUserFromProject();
                } else if (input.equalsIgnoreCase("Completion")) {
                    changeProjectCompletion();

                } else if (input.equalsIgnoreCase("set start date")) {
                    setStartDateProject();

                } else if (input.equalsIgnoreCase("set deadline")) {
                    setDeadlineProject();
                }

                insideProjectValue = Integer.parseInt(input);
            } catch (NumberFormatException | InvalidDateException e) {
                insideProjectValue = 0;
            }
        }
        refreshProjectInfoObject();
    }


    private static void enterActivity() { // Author Asger Allin Jensen

        int enterProjectValue = 0;
        while(true){
            if(enterProjectValue == 0){
                showActivityMenu();
            }
            String input = programScanner.nextLine();
            try {
                if (input.equalsIgnoreCase("Log")) {
                    logTimeOnActivity();

                }
                else if (input.equalsIgnoreCase("Completion")){
                    changeActivityCompletion();
                    showProjectMenu();
                    break;

                }else if(input.equalsIgnoreCase("Exit")){
                    showProjectMenu();
                    break;
                }else if(input.equalsIgnoreCase("Assign")){
                    assignUserActivity();

                }else if(input.equalsIgnoreCase("See Time Worked")){
                    System.out.println(currentActivityInfo.timeMapToString());
                }else if(input.equalsIgnoreCase("Remove")) {
                    removeUserActivity();

                } else if (input.equalsIgnoreCase("find free employee")) {
                    printFreeEmployees(app.findFreeEmployee(currentActivityInfo));
                } else if (input.equalsIgnoreCase("set start date")) {
                    setStartDateActivity();


                } else if (input.equalsIgnoreCase("set deadline")) {
                    setDeadlineActivity();
                }
                enterProjectValue = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                enterProjectValue = 0;
            }
            refreshActivityInfos();
        }
    }






    //----Login and register methods------------------------------------------------------------------

    private static void createUser() throws UserIdAlreadyInUseExeption, UserIdDoesNotExistExeption { // Author: Lovro Antic
        String userId = (programScanner.nextLine());
        try {
            if(userId.isEmpty()){
                System.out.println("Please enter a valid user id");
            }else{
                app.registerUser(userId);
                String newId = app.createUserId(userId);
                System.out.println("Your user id is: "+ app.getActualId(newId));
            }
        } catch (UserIdAlreadyInUseExeption | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again, press the 'Enter' button to continue");
        }


    }

    private static void loginUser() throws UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException { // Author: Lovro Antic
        String loginString = programScanner.next();
        app.logInUser(loginString);
        currentUserInfo = app.getCurrentUserInfo();
        showMainMenu();
    }

    private static void logTimeOnActivity(){ // Author: Nikolaj Vorndran Thygesen
        System.out.println("Enter hours worked today:");
        String workedTimeString = programScanner.nextLine();
        try {
            double workedTime = Double.parseDouble(workedTimeString);
            app.logTimeOnActivity(workedTime, currentActivityInfo);
        }catch (Exception e){
            System.out.println("Invalid time input");
        }
    }

    //----Leave methods------------------------------------------------------------------------------
    private static void registerNewLeave() { // Author: Nikolaj Vorndran Thygesen
        System.out.println("Please enter name of leave");
        String name = programScanner.nextLine();
        Calendar[] set = getDateSetFromUser();
        app.registerLeave(name,set[0],set[1]);
    }

    private static void removeLeave() { // Author: Nikolaj Vorndran Thygesen
        System.out.println("Please enter name of leave");
        String name = programScanner.nextLine();
        Calendar[] set = getDateSetFromUser();
        app.removeLeave(name,set[0],set[1]);
    }


    //----Create events------------------------------------------------------------------------------
    private static void newProject() { // Author
        System.out.println("Enter Name of project: ");
        String name = programScanner.nextLine();
        app.createProject(name);
    }

    private static void newActivityInProject(){ // Author: Asger Allin Jensen
        System.out.print("Enter activity name: " );
        String newActivityName = programScanner.nextLine();
        double numberIn = 0;
        while (!(numberIn > 0)) {
            System.out.print("Enter amount of budgeted hours: ");
            String numberInput = programScanner.nextLine();
            try {
                numberIn = Double.parseDouble(numberInput);
            } catch (NumberFormatException e) {
                numberIn = 0;
            }
        }
        app.createNewActivity(newActivityName, numberIn, currentProjectInfo);
    }

    //----Set dates-----------------------------------------------------------------------------------
    private static void setDeadlineProject() throws InvalidDateException { // Author: Niklas Emil Lysdal
        boolean success = false;
        while (!success) {
            try {
                Calendar c = getWeekOfYearFromUser();
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

    private static void setStartDateProject() throws InvalidDateException { // Author: Niklas Emil Lysdal
        boolean success = false;
        while (!success) {
            try {
                Calendar c = getWeekOfYearFromUser();
                app.setProjectStartDate(c, currentProjectInfo);
                refreshProjectInfoObject();
                success = true;

            } catch (InvalidDateException e) {
                System.out.print(e.getMessage());
                System.out.println("please try again;");
            }
        }

    }

    private static void setDeadlineActivity() { //Author: Niklas Emil Lysdal
        boolean success = false;
        while (!success) {
            try {
                Calendar c = getWeekOfYearFromUser();
                app.setActivityDeadlineFromInfo(currentActivityInfo,c);
                success=true;
            } catch (InvalidDateException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }

        }
    }

    private static void setStartDateActivity() { // Author: Niklas Emil Lysdal
        boolean success =false;
        while(!success) {
            try {
                Calendar c = getWeekOfYearFromUser();
                app.setActivityStartDateFromInfo(currentActivityInfo, c);
                success = true;
            } catch(InvalidDateException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
    }

    //----Manage Participants-------------------------------------------------------------------------

    private static void removeUserActivity() { // Author: Lovro Antic
        System.out.println("Enter user id of user to be removed from activity");
        String removeName = programScanner.nextLine();
        try{
            app.removeUserFromActivity(removeName, currentActivityInfo);
        }catch(UserIdDoesNotExistExeption e) {
            System.out.println(e.getMessage());
        }
    }

    private static void assignUserActivity() { // Author: Nikolaj Antic
        System.out.println("Enter user id of user to be added to activity");
        String addName = programScanner.nextLine();
        try {
            app.assignUserToActivity(addName, currentActivityInfo);
        } catch (UserIdDoesNotExistExeption e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addUserToProject() { // Author: Asger Allin Jensen
        System.out.println("Enter user id of user to be added to project");
        String addName = programScanner.nextLine();
        try{
            app.assignUserToProject(addName, currentProjectInfo);
        } catch (UserIdDoesNotExistExeption e) {
            System.out.println(e.getMessage());
        }
    }

    private static void removeUserFromProject() { // Author: Nikolaj Antic
        System.out.println("Enter user id of user to be removed from the project");
        String removeName = programScanner.nextLine();

        try {
            app.removeUserFromProject(removeName, currentProjectInfo);
        } catch (UserIdDoesNotExistExeption e) {
            System.out.println(e.getMessage());
        }
    }

    //Print methods---------------------------------------------------------------------------------

    private static void coreMenu() { // Author: Asger Allin Jensen
        System.out.println("\n____________________________________________________________________________________________________________________");
        System.out.println("Enter:\n'1' to log in \n'2' to register a new user, \n'ids' to see all user ids,\n'Enable demo mode' to enable a default config\n'Exit' to exit the program");
    }

    private static void showUserMenu(){// Author: Asger Allin Jensen
        System.out.println();
        System.out.println("____________________________________________________________________________________________________________________");
        System.out.println("Logged in with user Id: "+ app.getCurrentUserId());
        System.out.println("List of projects:");
        System.out.println(app.getProjectListString());
        System.out.println(app.getCurrentUserActivityListString());
        System.out.println("Enter: \nthe project id to enter a project, \n\"NEW\" to make a new project, \n\"LEAVE\" to register leave, \n\"REMOVE LEAVE\" to remove leave or \n\"Exit\" to exit app");

    }

    private static void showProjectMenu(){ // Author: Asger Allin Jensen
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

    private static void showActivityMenu() { // Author: Asger Allin Jensen
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

    private static void printFreeEmployees(ArrayList<FinalUserCount> results) { // Author: Niklas Emil Lysdal
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
    private static Calendar getWeekOfYearFromUser () { // author: Niklas Emil Lysdal
        System.out.println("What year? (format: yyyy)");
        boolean successfullYear = false;
        int year=1;
        Calendar c= Calendar.getInstance();
        c.clear();
        c.set(Calendar.YEAR,year);
        while (!successfullYear) {
            try {
                year = Integer.parseInt(programScanner.nextLine());
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
                week= Integer.parseInt(programScanner.nextLine());
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


    private static Calendar[] getDateSetFromUser () { // Author: Niklas Emil Lysdal
        Calendar[] set  = new Calendar[2];
        boolean validSet = false;
        Calendar start=null;
        Calendar end=null;

        while(!validSet) {
            System.out.println("First start date:");
            start = getDateFromUser();
            System.out.println("Now end date:");
            end = getDateFromUser();
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

    private static Calendar getDateFromUser() { // Author: Nikolaj Vorndran Thygesen

        Calendar c = new GregorianCalendar();
        boolean success = false;
        int year=0;
        while (!success) {
            System.out.println("Enter year (yyyy):");
            try {
                year = programScanner.nextInt();
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
                month = programScanner.nextInt();
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
                day = programScanner.nextInt();
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

    private static void changeProjectCompletion() { // Author: Asger Allin Jensen
        System.out.println("is project complete?(Y/N)");
        boolean success =false;
        String input ="";
        while(!success) {
            input = programScanner.nextLine();
            if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
                success=true;
            }
        }

        boolean newValue= input.equalsIgnoreCase("Y");
        app.changeCompletenessOfProject(newValue,currentProjectInfo);
        currentProjectInfo=app.renewProjectInfo(currentProjectInfo);
    }

    private static void changeActivityCompletion() { // Author: Asger Allin Jensen
        System.out.println("is activity complete?(Y/N)");
        boolean success =false;
        String input ="";
        while(!success) {
            input = programScanner.nextLine();
            if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
                success=true;
            }
        }
        boolean newValue= input.equalsIgnoreCase("Y");
        app.ChangeCompletenessOfActivity(newValue,currentActivityInfo);
        currentActivityInfo=app.renewActivityInfo(currentActivityInfo);
    }


    //----- Refresh infos--------------------------------------------------------------------------------

    private static void refreshProjectInfoObject(){ // Author: Lovro Antic
        currentProjectInfo = app.renewProjectInfo(currentProjectInfo);
    }

    private static void refreshActivityInfos() { // Author: Lovro Antic
        currentActivityInfo = app.renewActivityInfo(currentActivityInfo);
    }


    private static void refreshUserInfo() { // Author: Nikolaj Vorndran Thygesen
        currentUserInfo = app.renewUserInfo(currentUserInfo);
    }

}
