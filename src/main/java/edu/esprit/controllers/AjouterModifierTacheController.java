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
import java.time.ZoneId;
import java.util.Date;

import edu.esprit.entities.Tache;
import edu.esprit.services.ServiceTache;
import edu.esprit.services.EtatTache;

public class AjouterModifierTacheController {

    @FXML
    private Button Exit;
    @FXML
    private Button ajouterButton;
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

    private ServiceTache serviceTache;
    private int selectedTaskId; // Holds the ID of the task being modified
    private Stage stage; // Reference to the stage

    public AjouterModifierTacheController() {
        this.serviceTache = new ServiceTache();
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
/*
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
*/
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

    public void initModifier(Tache tache) {
        this.selectedTaskId = tache.getId_T(); // Set the ID of the task being modified
        ajouterButton.setText("Modifier");
        categoryField.setText(tache.getCategorie_T());
        titleField.setText(tache.getTitre_T());
        attachmentField.setText(tache.getPieceJointe_T());
        descriptionField.setText(tache.getDesc_T());

        // Convert java.sql.Date to java.util.Date
        java.util.Date startDateUtil = new java.util.Date(tache.getDate_DT().getTime());
        java.util.Date endDateUtil = new java.util.Date(tache.getDate_FT().getTime());

        // Convert java.util.Date to LocalDate
        LocalDate startDate = startDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = endDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        startDatePicker.setValue(startDate);
        endDatePicker.setValue(endDate);

        switch (tache.getEtat_T()) {
            case TO_DO:
                toDoRadioButton.setSelected(true);
                break;
            case DOING:
                doingRadioButton.setSelected(true);
                break;
            case DONE:
                doneRadioButton.setSelected(true);
                break;
        }
    }

    @FXML
    void ajouterTache(ActionEvent event) {
        try {
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

            // Validate input fields
            if (category.isEmpty() || title.isEmpty() || startDate == null || endDate == null) {
                throw new IllegalArgumentException("Veuillez remplir tous les champs obligatoires.");
            }

            // Create a new task object
            Tache newTask = new Tache(category, title, attachment, null, null, description, etat, null);

            // Set start and end dates if not null
            if (endDate != null) {
                newTask.setDate_DT(java.sql.Date.valueOf(startDate));
                newTask.setDate_FT(java.sql.Date.valueOf(endDate));
            }
            if (stage != null) {
                stage.close(); // Check if stage is not null before calling close()
            } else {
                System.out.println("Stage is null, cannot close.");
            }
            // Check if it's a modification
            if (ajouterButton.getText().equals("Modifier")) {
                // Retrieve the existing task by ID for modification
                Tache existingTache = serviceTache.getOneByID(selectedTaskId);
                if (existingTache != null) {
                    existingTache.setCategorie_T(category);
                    existingTache.setTitre_T(title);
                    existingTache.setPieceJointe_T(attachment);
                    existingTache.setDesc_T(description);
                    existingTache.setDate_DT(newTask.getDate_DT());
                    existingTache.setDate_FT(newTask.getDate_FT());
                    existingTache.setEtat_T(etat);

                    // Check if user is not null before accessing getId()
                    serviceTache.modifier(existingTache);
                    clearFields();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Tache modifiée avec succès.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "La tache à modifier n'a pas été trouvée.");
                }
            }
            if (ajouterButton.getText().equals("Ajouter")) {

                try {
                    this.stage= this.stage;
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
            else return;
            stage.close();
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
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

    public void Exit(ActionEvent actionEvent) {
        // Get the reference to the scene
        stage.close();
    }

}
