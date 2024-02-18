package edu.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainTFX extends Application {
    public static Stage stg;
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        stg = primaryStage;
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/esprit/gui/AjoutTache.fxml")));

        //Scene scene= new Scene(root);

        primaryStage.setTitle("Baladity");
        //primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
