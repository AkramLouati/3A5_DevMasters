package edu.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Objects;

public class MainFx extends Application {
    @Override
    public void start(Stage stage) throws Exception {
//        InputStream iconStream = getClass().getResourceAsStream("/assets/icon.png");
//        if (iconStream != null) {
//            stage.getIcons().add(new Image(iconStream));
//        } else {
//            System.err.println("Icon file not found or InputStream is null.");
//        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
