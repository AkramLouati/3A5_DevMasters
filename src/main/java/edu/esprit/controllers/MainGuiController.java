/*package edu.esprit.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainGuiController {

    @FXML
    private BorderPane MainAnchorPaneBaladity;

    @FXML
    private BorderPane SecondBorderPane;

    @FXML
    private VBox MainLeftSidebar;

    private boolean isSidebarVisible = true;

    @FXML
    void BTNToggleSidebar(ActionEvent event) {
        TranslateTransition sideBarTransition = new TranslateTransition(Duration.millis(200), MainLeftSidebar);

        double sidebarWidth = 308;
        double hiddenWidth = 0; // Width when sidebar is hidden

        if (isSidebarVisible) {
            // Hide sidebar
            sideBarTransition.setToX(-sidebarWidth);
            isSidebarVisible = false;
            // Set the width of the MainLeftSidebar when sidebar is hidden
            MainLeftSidebar.setPrefWidth(hiddenWidth);
            // Set the width of the SecondBorderPane when sidebar is hidden
            SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() + sidebarWidth);
        } else {
            // Show sidebar
            sideBarTransition.setToX(0);
            isSidebarVisible = true;
            // Set the width of the MainLeftSidebar back to its original value
            MainLeftSidebar.setPrefWidth(MainLeftSidebar.getWidth() + sidebarWidth);
            // Set the width of the SecondBorderPane back to its original value
            SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() - sidebarWidth);
        }

        sideBarTransition.play();
    }


    public void BTNGestionEvennement(ActionEvent actionEvent) {

    }

    public void BTNGestionUser(ActionEvent actionEvent) {
    }

    public void BTNGestionRec(ActionEvent actionEvent) {
    }

    public void BTNGestionAct(ActionEvent actionEvent) {

    }

    public void BTNGestionEquipement(ActionEvent actionEvent) {
        try {
            // Close any previous stage if it exists
            Stage stage = new Stage();
            if (stage != null) {
                stage.close();
            }


            // Load AfficherTache.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipement.fxml"));
            AnchorPane afficherAP = loader.load();

            // Set the stage to the controller
            AfficherEquipementController controller = loader.getController();
            controller.setStage(stage);

            // Set AfficherTache view into MainAnchorPaneBaladity
            MainAnchorPaneBaladity.getChildren().setAll(afficherAP);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load AfficherEquipement view");
        }
    }

    public void BTNGestionTache(ActionEvent actionEvent) {
    }
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}

*/