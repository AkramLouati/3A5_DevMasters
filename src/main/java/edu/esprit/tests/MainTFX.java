package edu.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class MainTFX extends Application {
    public static Stage stg;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stg = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        InputStream fxmlStream = getClass().getResourceAsStream("/AjouterTache.fxml");
        if (fxmlStream == null) {
            throw new IOException("FXML file not found");
        }
        // Set the location of the FXML file in the loader
        Scene scene = new Scene(loader.load(fxmlStream), 1280, 720);

        primaryStage.setTitle("Baladity");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
