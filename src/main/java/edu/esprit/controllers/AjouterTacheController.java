package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Attachment File");
        // Set extension filters if needed
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            attachmentField.setText(selectedFile.getAbsolutePath());
        }
    }


    @FXML
    void ajouterTache(ActionEvent event) {
        String category = categoryField.getText();
        String title = titleField.getText();
        String attachment = attachmentField.getText();
        String description = descriptionField.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        EtatTache etat;

        if (toDoRadioButton.isSelected()) {
            etat = EtatTache.TO_DO;
        } else if (doingRadioButton.isSelected()) {
            etat = EtatTache.DOING;
        } else {
            etat = EtatTache.DONE;
        }

        // Check if required fields are empty
        if (category.isEmpty() || title.isEmpty() || attachment.isEmpty() || startDate == null || endDate == null || etat == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Veuillez remplir tous les champs obligatoires.");
            return; // Stop further execution
        }

        try {
            // Set the selected user to user with ID 14
            ServiceUser serviceUser = new ServiceUser();
            EndUser selectedUser = serviceUser.getOneByID(14);

            // Check if selectedUser is not null
            if (selectedUser != null) {
                Date startDateSql = java.sql.Date.valueOf(startDate);
                Date endDateSql = java.sql.Date.valueOf(endDate);

                Tache tache = new Tache(category, title, attachment, startDateSql, endDateSql, description, etat, selectedUser);

                serviceTache.ajouter(tache);
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Tache ajoutee avec succes.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Utilisateur non trouvé avec l'ID 14.");
            }
        } catch (IllegalArgumentException e) {
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
        // Clear radio button selection
        doingRadioButton.setSelected(false);
        doneRadioButton.setSelected(false);
    }


    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
