package presentation;

import app.AUserIsAlreadyLoggedInException;
import app.UserIdAlreadyInUseExeption;
import app.UserIdDoesNotExistExeption;
import domain.App;
import domain.Project;

import java.util.Scanner;

public class Viewer { // Author Asger
    public static void main(String[] args) throws UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException, UserIdAlreadyInUseExeption {
        // App setup
        App app = new App();
        Scanner loginScanner = new Scanner(System.in);

        // Login & Register user Slice
        int startvalue = -1;
        while(true){
            if (startvalue == 1){
                System.out.println("Enter User id");
                app.logInUser(loginScanner.next().substring(0,4).toUpperCase());
                break;
            }else if (startvalue == 2){
                System.out.println("Enter name to create user id");
                app.registerUser(loginScanner.next());
            }else{
                System.out.println("Enter '1' to log in, or '2' to register a new user ");
            }
            String input = loginScanner.nextLine();
            try {
                startvalue = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                startvalue = -1;
            }
        }
        loginScanner.close();

        // Project overview and create project slice
        startvalue = 0;
        Scanner programScanner = new Scanner(System.in);
        while((app.getCurrentUser() != null)){
            if (startvalue > 0){
                Project currentproject = app.getProjectRepository().get(startvalue-1);
            }else if(startvalue == 0){
                projectOverview(app);
            }
            programScanner.nextLine();
            String input = programScanner.nextLine();
            try {
                if (input.equalsIgnoreCase("NEW")){
                    newProject(app);
                }
                startvalue = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                startvalue = 0;
            }
        }


    }

    private static void newProject(App app) {
        Scanner newProjectScanner = new Scanner(System.in);
        System.out.println("Enter Name of project");
        String name = newProjectScanner.nextLine();
        app.createProject(name);
        newProjectScanner.close();
    }

    private static void projectOverview(App app){
        System.out.println("Logged in with user Id: "+ app.getCurrentUserId());
        System.out.println("List of projects:");
        int i = 1;
        for(Project project : app.getProjectRepository()){
            System.out.println(i + ": " +project.getProjectID() + " Name: " + project.getTitle());
            i++;
        }
        System.out.println("Enter the number for the project, \"NEW\" to make a new project, or \"Exit\" to exit app");
    }
}
