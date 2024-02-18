package edu.esprit.tests;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;



public class MainBS  extends Application{

    public static Stage stg;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        stg = primaryStage;

        // Create a blank scene with no root node
        Scene scene = new Scene(null, 400, 400);

        primaryStage.setTitle("Baladity");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
