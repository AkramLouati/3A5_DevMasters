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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class AjouterTacheController {

    public BorderPane firstborderpane;
    ServiceUser serviceUser = new ServiceUser();
    EndUser user01 = serviceUser.getOneByID(14);
    @FXML
    private AnchorPane MainAnchorPaneBaladity;
    @FXML
    private BorderPane SecondBorderPane;
    @FXML
    private VBox MainLeftSidebar;
    private boolean isSidebarVisible = true;
    private ValidationSupport validationSupport;

    @FXML
    private ImageView PieceJointeImage;
    @FXML
    private TextField titleField, descriptionField, RECRadioButton;
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

    public AjouterTacheController() {
        this.serviceCategorieT = new ServiceCategorieT();
        this.serviceTache = new ServiceTache();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void BTNToggleSidebar(ActionEvent event) {
        TranslateTransition sideBarTransition = new TranslateTransition(Duration.millis(400), MainLeftSidebar);
        double sidebarWidth = MainLeftSidebar.getWidth();
        if (isSidebarVisible) {
            // Hide sidebar
            sideBarTransition.setByX(-sidebarWidth);
            isSidebarVisible = false;
            // Adjust the width of SecondBorderPane
            SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() + sidebarWidth);
            // Translate SecondBorderPane to the left to take the extra space
            TranslateTransition borderPaneTransition = new TranslateTransition(Duration.millis(250), SecondBorderPane);
            borderPaneTransition.setByX(-sidebarWidth);
            borderPaneTransition.play();
        } else {
            // Show sidebar
            sideBarTransition.setByX(sidebarWidth);
            isSidebarVisible = true;
            // Adjust the width of SecondBorderPane
            SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() - sidebarWidth);
            // Reset the translation of SecondBorderPane to 0
            TranslateTransition borderPaneTransition = new TranslateTransition(Duration.millis(250), SecondBorderPane);
            borderPaneTransition.setByX(sidebarWidth);
            borderPaneTransition.play();
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
    }

    public void BTNGestionTache(ActionEvent actionEvent) {

    }

    @FXML
    void browseForImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Attachment File");

        // Set initial directory
        String initialDirectory = "src/main/resources/img";
        File initialDirFile = new File(initialDirectory);

        // Check if the initial directory is valid
        if (initialDirFile.exists() && initialDirFile.isDirectory()) {
            fileChooser.setInitialDirectory(initialDirFile);
        } else {
            System.err.println("Initial directory is not valid: " + initialDirectory);
            // You might want to handle this error condition appropriately
            return;
        }

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                String fileUrl = selectedFile.toURI().toURL().toString();
                // Créer une instance d'Image à partir de l'URL de fichier
                Image image = new Image(fileUrl);
                // Définir l'image dans l'ImageView
                PieceJointeImage.setImage(image);
            } catch (MalformedURLException e) {
                // Gérer l'exception si le chemin d'accès à l'image n'est pas valide
                e.printStackTrace();
            }
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
    void AjouterTache(ActionEvent event) {
        if (!validateFields()) {
            return;
        }
        if (!toDoRadio.isSelected() && !doingRadio.isSelected() && !doneRadio.isSelected()) {
            // Shake the RECRadioButton text field and return
            shakeNode(RECRadioButton);
            return;
        }
        try {
            EtatTache etat;
            String categoryName = categoryField.getValue();
            String title = titleField.getText();
            String attachment = ""; // Initialize attachment as an empty string
            Image image = PieceJointeImage.getImage();
            if (image != null) {
                attachment = image.getUrl();
            }
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


            Tache tache = new Tache(categorie, title, attachment, startDateSql, endDateSql, description, etat, user01);

            serviceTache.ajouter(tache);
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Tache ajoutée avec succès.");

        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private boolean validateFields() {
        ValidationResult validationResult = validationSupport.validationResultProperty().get();
        boolean isValid = validationResult == null || validationResult.getErrors().isEmpty();

        if (!isValid) {
            clearShakeEffects();
            for (Control control : validationSupport.getRegisteredControls()) {
                if (validationResult.getMessages().stream().anyMatch(message -> message.getTarget() == control && message.getSeverity() == Severity.ERROR)) {
                    shakeNode(control);
                }
            }
        } else {
            clearShakeEffects();
        }
        return isValid;
    }

    private void clearShakeEffects() {
        titleField.setStyle("-fx-border-color: transparent;");
        categoryField.setStyle("-fx-border-color: transparent;");
        startDatePicker.setStyle("-fx-border-color: transparent;");
        endDatePicker.setStyle("-fx-border-color: transparent;");
        RECRadioButton.setStyle("-fx-border-color: transparent;");
    }

    private void clearFields() {
        categoryField.getSelectionModel().clearSelection();
        titleField.clear();
        PieceJointeImage.setImage(null);
        descriptionField.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        toDoRadio.setSelected(false);
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
        validationSupport = new ValidationSupport();

        // Add validators for each field
        validationSupport.registerValidator(titleField, (Control c, String newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                return ValidationResult.fromMessageIf(c, "Titre Obligatoire", Severity.ERROR, true);
            } else if (newValue.matches(".*\\d+.*")) {
                return ValidationResult.fromMessageIf(c, "Titre Ne Contient Pas Des Nombres", Severity.ERROR, true);
            }
            return null; // Return null if validation passes
        });
        validationSupport.registerValidator(categoryField, Validator.createEmptyValidator("Categorie Obligatoire"));
        validationSupport.registerValidator(startDatePicker, (Control c, LocalDate newValue) -> {
            if (newValue == null) {
                return ValidationResult.fromMessageIf(c, "Date Debut Obligatoire", Severity.ERROR, true);
            } else if (endDatePicker.getValue() != null && newValue.isAfter(endDatePicker.getValue())) {
                return ValidationResult.fromMessageIf(c, "Date Debut est apres Date Fin", Severity.ERROR, true);
            }
            return null; // Return null if validation passes
        });

        // Custom validator for end date
        validationSupport.registerValidator(endDatePicker, (Control c, LocalDate newValue) -> {
            if (newValue == null) {
                return ValidationResult.fromMessageIf(c, "Date fin Obligatoire", Severity.ERROR, true);
            } else if (startDatePicker.getValue() != null && newValue.isBefore(startDatePicker.getValue())) {
                return ValidationResult.fromMessageIf(c, "Date fin est avant Date Debut", Severity.ERROR, true);
            }
            return null; // Return null if validation passes
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
}
