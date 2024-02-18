package edu.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

import edu.esprit.entities.Tache;
import edu.esprit.services.ServiceTache;
import edu.esprit.services.EtatTache;

public class AjouterTacheController {

    public Button ajouterButton;
    @FXML
    private TextField attachmentField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField descriptionField;

    @FXML
    private RadioButton doingRadioButton;

    @FXML
    private RadioButton doneRadioButton;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField titleField;

    @FXML
    private RadioButton toDoRadioButton;

    @FXML
    private ServiceTache serviceTache;

    public AjouterTacheController() {
        this.serviceTache = new ServiceTache();
    }

    @FXML
    void afficherListeAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTache.fxml"));
            Parent root = loader.load();

            // Afficher la scène avec les données des fournisseurs
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Erreur lors de l'affichage de la liste des Taches : " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void browseForImage(ActionEvent event) {
        // Add functionality for browsing attachment here if needed
    }

    @FXML
    void ajouterTache(ActionEvent event) {
        String category = categoryField.getText();
        String title = titleField.getText();
        String attachment = attachmentField.getText();
        String description = descriptionField.getText();
        Date startDate = java.sql.Date.valueOf(startDatePicker.getValue());
        Date endDate = java.sql.Date.valueOf(endDatePicker.getValue());
        EtatTache etat;

        if (toDoRadioButton.isSelected()) {
            etat = EtatTache.TO_DO;
        } else if (doingRadioButton.isSelected()) {
            etat = EtatTache.DOING;
        } else {
            etat = EtatTache.DONE;
        }

        Tache tache = new Tache(category, title, attachment, startDate, endDate, description, etat, null); // Assuming user is not set here
        try {
            serviceTache.ajouter(tache);
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Tache ajoutee avec succes.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Erreur lors de l'ajout de la tache : " + e.getMessage());
        }
    }

    private void clearFields() {
        categoryField.clear();
        titleField.clear();
        attachmentField.clear();
        descriptionField.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        toDoRadioButton.setSelected(true);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
