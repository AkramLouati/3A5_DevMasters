package edu.esprit.tests;

import edu.esprit.services.ServiceUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class MainFX extends Application {
    public static Stage stg;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ServiceUser user = new ServiceUser();
        Session.setCurrentUser(user.getOneByID(13));
        //        Session.setCurrentUser(user.getOneByID(13));
        stg = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        InputStream fxmlStream = getClass().getResourceAsStream("/EventDashboard.fxml");
        if (fxmlStream == null) {
            throw new IOException("FXML file not found");
        }
        // Set the location of the FXML file in the loader
        Scene scene = new Scene(loader.load(fxmlStream));

        primaryStage.setTitle("Baladity");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.show();
    }

}
