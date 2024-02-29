package edu.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class ModifierAvisGuiFront {
    @FXML
    private AnchorPane MainAnchorPaneBaladity;

    @FXML
    private BorderPane SecondBorderPane;

    @FXML
    private TextArea commentaireeqmodif;

    @FXML
    private DatePicker dateaviseqmodif;

    @FXML
    private BorderPane firstborderpane;

    @FXML
    private Button modifierButton;

    @FXML
    private ComboBox<Integer> noteeqmodif;

    @FXML
    private Button retourButton;

    @FXML
    void BTNGestionAct(ActionEvent event) {

    }

    @FXML
    void BTNGestionEquipement(ActionEvent event) {

    }

    @FXML
    void BTNGestionEvennement(ActionEvent event) {

    }

    @FXML
    void BTNGestionRec(ActionEvent event) {

    }

    @FXML
    void BTNGestionTache(ActionEvent event) {

    }

    @FXML
    void modifierAvisAction(ActionEvent event) {

    }

    @FXML
    void retourAvisAction(ActionEvent event) {
        try {
            System.out.println("Resource URL: " + getClass().getResource("/AfficherAvisGuiFront.fxml"));
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherAvisGuiFront.fxml")));
            retourButton.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
}

