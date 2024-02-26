package edu.esprit.tests;

import edu.esprit.controllers.MainGuiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainTFX extends Application {
    public static Stage stg;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainGui.fxml"));
        Parent root = loader.load();
        MainGuiController mainGuiController = loader.getController();

        // Set the UserData of the stage
        primaryStage.setUserData(mainGuiController);

        // Set the scene
        Scene scene = new Scene(root);
        primaryStage.setTitle("Baladity");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.show();
    }
}
