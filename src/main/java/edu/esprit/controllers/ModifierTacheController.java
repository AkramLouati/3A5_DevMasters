package edu.esprit.controllers;

import edu.esprit.entities.CategorieT;
import edu.esprit.entities.EndUser;
import edu.esprit.entities.Tache;
import edu.esprit.services.EtatTache;
import edu.esprit.services.ServiceCategorieT;
import edu.esprit.services.ServiceTache;
import edu.esprit.services.ServiceUser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ModifierTacheController {

    ServiceUser serviceUser = new ServiceUser();
    EndUser user01 = serviceUser.getOneByID(14);

    @FXML
    private TextField titleField, attachmentField, descriptionField, RECRadioButton;
    @FXML
    private ComboBox<String> categoryField;
    @FXML
    private RadioButton toDoRadio, doneRadio, doingRadio;
    @FXML
    private DatePicker startDatePicker, endDatePicker;
    private ServiceCategorieT serviceCategorieT;
    private ServiceTache serviceTache;
    private int selectedTaskId;
    private Stage stage;

    public ModifierTacheController() {
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

    public void initModifier(Tache tache) {
        try {
            this.selectedTaskId = tache.getId_T();
            titleField.setText(tache.getTitre_T());
            attachmentField.setText(tache.getPieceJointe_T());
            descriptionField.setText(tache.getDesc_T());
            // Convert java.sql.Date to java.util.Date
            Date startDateUtil = new Date(tache.getDate_DT().getTime());
            Date endDateUtil = new Date(tache.getDate_FT().getTime());

            // Convert java.util.Date to LocalDate
            LocalDate startDate = startDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = endDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            startDatePicker.setValue(startDate);
            endDatePicker.setValue(endDate);

            switch (tache.getEtat_T()) {
                case TO_DO:
                    toDoRadio.setSelected(true);
                    break;
                case DOING:
                    doingRadio.setSelected(true);
                    break;
                case DONE:
                    doneRadio.setSelected(true);
                    break;
            }

            // Get the category name associated with the Tache object
            String categoryName = tache.getCategorie().getNom_Cat();
            // Set the value of the ComboBox to the category name
            categoryField.setValue(categoryName);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void shakeNode(Node node) {
        String redBorder = "-fx-border-color: red;";
        String blackBorder = "-fx-border-color: black;";
        node.setStyle(redBorder);
        TranslateTransition tt = new TranslateTransition(Duration.millis(100), node);
        tt.setFromX(0f);
        tt.setByX(10f);
        tt.setCycleCount(4);
        tt.setAutoReverse(true);
        tt.play();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> node.setStyle(blackBorder)));
        timeline.play();
    }

    @FXML
    void ModifierTache(ActionEvent event) {
        try {
            // Check if any field is null or empty
            boolean hasEmptyFields = false;
            if (titleField.getText().isEmpty()) {
                shakeNode(titleField);
                hasEmptyFields = true;
            }
            if (categoryField.getValue() == null) {
                shakeNode(categoryField);
                hasEmptyFields = true;
            }
            if (startDatePicker.getValue() == null) {
                shakeNode(startDatePicker);
                hasEmptyFields = true;
            }
            if (endDatePicker.getValue() == null) {
                shakeNode(endDatePicker);
                hasEmptyFields = true;
            }
            if (!toDoRadio.isSelected() && !doingRadio.isSelected() && !doneRadio.isSelected()) {
                shakeNode(RECRadioButton);
                hasEmptyFields = true;
            }

            if (hasEmptyFields) {
                return; // Stop further execution
            }

            EtatTache etat;
            String categoryName = categoryField.getValue();
            String title = titleField.getText();
            String attachment = attachmentField.getText();
            String description = descriptionField.getText();

            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();

            Date startDateSql = java.sql.Date.valueOf(startDate);
            Date endDateSql = java.sql.Date.valueOf(endDate);


            if (toDoRadio.isSelected()) {
                etat = EtatTache.TO_DO;
            } else if (doingRadio.isSelected()) {
                etat = EtatTache.DOING;
            } else if (doneRadio.isSelected()) {
                etat = EtatTache.DONE;
            } else {
                etat = null;
            }

            if (stage != null) {
                Exit(new ActionEvent());
            } else {
                System.out.println("Stage is null, cannot close.");
            }

            // Retrieve the CategorieT object associated with the selected category name
            CategorieT categorie = serviceCategorieT.getCategoryByName(categoryName);

            Tache existingTache = serviceTache.getOneByID(selectedTaskId);
            if (existingTache != null) {
                existingTache.setId_T(existingTache.getId_T());
                existingTache.setCategorie(categorie);
                existingTache.setTitre_T(title);
                existingTache.setPieceJointe_T(attachment);
                existingTache.setDesc_T(description);
                existingTache.setDate_DT(startDateSql);
                existingTache.setDate_FT(endDateSql);
                existingTache.setEtat_T(etat);
                existingTache.setUser(user01);
                serviceTache.modifier(existingTache);
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Tache modifiée avec succès.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "La Tache à modifier n'a pas été trouvée.");
            }
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
        toDoRadio.setSelected(true);
        doingRadio.setSelected(false);
        doneRadio.setSelected(false);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void Exit(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MainGui.fxml"));
            titleField.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    public void toDoRadioButton(ActionEvent actionEvent) {
        toDoRadio.setSelected(true);
        doingRadio.setSelected(false);
        doneRadio.setSelected(false);
    }

    public void doingRadioButton(ActionEvent actionEvent) {
        doingRadio.setSelected(true);
        toDoRadio.setSelected(false);
        doneRadio.setSelected(false);
    }

    public void doneRadioButton(ActionEvent actionEvent) {
        doneRadio.setSelected(true);
        toDoRadio.setSelected(false);
        doingRadio.setSelected(false);
    }

    @FXML
    public void initialize() {
        ValidationSupport validationSupport = new ValidationSupport();

        // Add validators for each field
        validationSupport.registerValidator(titleField, Validator.createEmptyValidator("Titre obligatoire"));
        validationSupport.registerValidator(categoryField, Validator.createEmptyValidator("Categorie obligatoire"));
        validationSupport.registerValidator(startDatePicker, Validator.createEmptyValidator("Date debut obligatoire"));
        validationSupport.registerValidator(endDatePicker, Validator.createEmptyValidator("Date fin obligatoire"));
        // Custom validator for radio buttons
        validationSupport.registerValidator(RECRadioButton, (Control c, String newValue) -> {
            boolean isAnySelected = toDoRadio.isSelected() || doingRadio.isSelected() || doneRadio.isSelected();
            return ValidationResult.fromMessageIf(c, "Etat Obligatoire", Severity.ERROR, !isAnySelected);
        });

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
