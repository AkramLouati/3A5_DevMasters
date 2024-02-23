package edu.esprit.controllers;

import edu.esprit.entities.CategorieT;
import edu.esprit.entities.EndUser;
import edu.esprit.entities.Tache;
import edu.esprit.services.EtatTache;
import edu.esprit.services.ServiceCategorieT;
import edu.esprit.services.ServiceTache;
import edu.esprit.services.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class AjouterTacheController {
    @FXML
    private TextField attachmentField;

    @FXML
    private ComboBox<String> categoryField;

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

    private ServiceCategorieT serviceCategorieT;
    private ServiceTache serviceTache;
    private int selectedTaskId;
    private Stage stage;

    ServiceUser serviceUser = new ServiceUser();
    EndUser user01 = serviceUser.getOneByID(14);
    public AjouterTacheController() {
        this.serviceCategorieT = new ServiceCategorieT();
        this.serviceTache = new ServiceTache();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void browseForImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Attachment File");

        // Set initial directory
        String initialDirectory = "src/main/java/edu/esprit/img";
        fileChooser.setInitialDirectory(new File(initialDirectory));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            attachmentField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    void AjouterTache(ActionEvent event) {
        try {
            String categoryName = categoryField.getValue();
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

            if (categoryName == null || title.isEmpty() || startDate == null || endDate == null) {
                throw new IllegalArgumentException("Veuillez remplir tous les champs obligatoires.");
            }

            Date startDateSql = java.sql.Date.valueOf(startDate);
            Date endDateSql = java.sql.Date.valueOf(endDate);

            if (stage != null) {
                Exit(new ActionEvent());
            } else {
                System.out.println("Stage is null, cannot close.");
            }

            // Retrieve the CategorieT object associated with the selected category name
            CategorieT categorie = serviceCategorieT.getCategoryByName(categoryName);


                Tache tache = new Tache(categorie, title, attachment, startDateSql, endDateSql, description, etat, user01);
                serviceTache.ajouter(tache);
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Tache ajoutée avec succès.");

        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void clearFields() {
        categoryField.getSelectionModel().clearSelection();
        titleField.clear();
        attachmentField.clear();
        descriptionField.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        toDoRadioButton.setSelected(true);
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
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherTache.fxml"));
            titleField.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    @FXML
    public void initialize() {
        populateCategoryComboBox();
    }

    private void populateCategoryComboBox() {
        try {
            List<String> categoryNames = serviceCategorieT.getAllCategoryNames();
            ObservableList<String> observableCategoryNames = FXCollections.observableArrayList(categoryNames);
            categoryField.setItems(observableCategoryNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
