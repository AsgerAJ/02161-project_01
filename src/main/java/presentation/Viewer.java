package presentation;

import app.AUserIsAlreadyLoggedInException;
import app.UserIdAlreadyInUseExeption;
import app.UserIdDoesNotExistExeption;
import domain.App;

import java.util.Scanner;

public class Viewer { // Author Asger
    public static void main(String[] args) throws UserIdDoesNotExistExeption, AUserIsAlreadyLoggedInException, UserIdAlreadyInUseExeption {
        // App setup
        App app = new App();
        Scanner scanner = new Scanner(System.in);


        int startvalue = -1;
        while(true){
            if (startvalue == 1){
                System.out.println("Enter User id");
                app.logInUser(scanner.next().substring(0,4).toUpperCase());
                break;
            }else if (startvalue == 2){
                System.out.println("Enter name to create user id");
                app.registerUser(scanner.next());
            }else{
                System.out.println("Enter '1' to log in, or '2' to register a new user ");
            }
            String input = scanner.nextLine();
            try {
                startvalue = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                startvalue = -1;
            }
        }

        boolean running = true;

        while(running && (app.getCurrentUser() != null)){
            System.out.println("Logged in with user Id: "+ app.getCurrentUserId());
            
        }


    }
}
