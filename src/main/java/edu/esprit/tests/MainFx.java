package edu.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.prefs.Preferences;


public class MainFx extends Application {

    // Checking login state on startup
    Preferences preferences = Preferences.userNodeForPackage(MainFx.class);
    String loggedInUser = preferences.get("current_user", null);

    @Override
    public void start(Stage stage) throws Exception {

        if (loggedInUser != null) {
            // User is already logged in, proceed to the main application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
//        Scene scene = new Scene(root,1310,845);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } else {
            // User is not logged in, show the login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserAccount.fxml"));
            Parent root = loader.load();
//        Scene scene = new Scene(root,1310,845);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
