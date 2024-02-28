package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Tache;
import edu.esprit.services.ServiceTache;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class AfficherTacheController implements Initializable {

    EndUser user = new EndUser(12);

    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scroll;
    private Tache taches = new Tache();
    private Stage stage; // Define a stage variable
    private ServiceTache sr = new ServiceTache();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Set<Tache> tacheList = sr.getAll();

        grid.getChildren().clear();

        int column = 0;
        int row = 1;
        try {
            for (Tache tache : tacheList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Tache.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                TacheController itemController = fxmlLoader.getController();
                itemController.setData(tache);

                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)

                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ajouterButton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTache.fxml"));
            Parent root = loader.load();
            AjouterTacheController controller = loader.getController();

            // Get the stage of the current scene
            Stage currentStage = (Stage) grid.getScene().getWindow();

            // Set the stage to the controller
            controller.setStage(currentStage);

            // Create a new scene with the root and set it on the current stage
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load AjouterTache view");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void PDFTacheLabel(ActionEvent actionEvent) {
    }

    public void ExcelTacheLabel(ActionEvent actionEvent) {
    }

    public void MeilleurEmpTLabel(ActionEvent actionEvent) {
    }

    public void searchTacheLabel(ActionEvent actionEvent) {
    }

}
