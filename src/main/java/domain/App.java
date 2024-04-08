package domain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {
    ArrayList<User> Users = new ArrayList<User>();
    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public ArrayList<User> getUsers() {
        return Users;
    }
}