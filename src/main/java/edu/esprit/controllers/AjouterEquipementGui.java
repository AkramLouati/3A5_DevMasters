package edu.esprit.controllers;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AjouterEquipementGui implements Initializable{
    @FXML
    private AnchorPane MainAnchorPaneBaladity;

    @FXML
    private VBox MainLeftSidebar;

    @FXML
    private BorderPane SecondBorderPane;

    @FXML
    private Button ajoutequipementbtn;

    @FXML
    private RadioButton categoriefixe;

    @FXML
    private RadioButton categoriemobile;

    @FXML
    private DatePicker dateajout;

    @FXML
    private TextArea descriptionTF;

    @FXML
    private BorderPane firstborderpane;

    @FXML
    private Label imageequipement;

    @FXML
    private ImageView imagevieweq;

    @FXML
    private Button navigateequipementbtn;

    @FXML
    private TextField nomTF;

    @FXML
    private ComboBox<Integer> quantiteCB;

    @FXML
    private TextField referenceTF;

    @FXML
    private Button telechargerimage;
    private boolean isSidebarVisible = true;
    private String imagePath;
    private final ServiceEquipement se = new ServiceEquipement();
    Muni muni = new Muni(1);
    EndUser user = new EndUser(1,muni);
    @FXML
    private Label referenceErrorLabel;
    @FXML
    private Label nomErrorLabel;
    @FXML
    private Label quantiteErrorLabel;

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
    void BTNGestionUser(ActionEvent event) {

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
            TranslateTransition borderPaneTransition = new TranslateTransition(Duration.millis(400), SecondBorderPane);
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

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setMainAnchorPaneContent(AnchorPane ajouterAP) {
        MainAnchorPaneBaladity.getChildren().setAll(ajouterAP);
    }
    private boolean isQuantiteSelected() {
        return quantiteCB.getValue() != null;
    }
    @FXML
    void ajouterEquipementAction(ActionEvent event) {
     // Utilisez imagePath pour enregistrer le chemin absolu de l'image dans la base de données
        Date dateAjout = Date.valueOf(dateajout.getValue()); // Convertissez la valeur du DatePicker en objet Date
        int quantite = Integer.parseInt(quantiteCB.getValue().toString()); // Assurez-vous que la ComboBox est correctement initialisée avec des valeurs
        String reference = referenceTF.getText();
        String referenceUpper = reference.toUpperCase(); // Convertir la référence en majuscules
        if (!reference.equals(referenceUpper) || reference.isEmpty()) {
            referenceErrorLabel.setText("La référence doit être en majuscules.");
            referenceErrorLabel.setVisible(true);
            referenceTF.requestFocus();
            return; // Arrêter la méthode si la référence n'est pas en majuscules
        } else {
            referenceErrorLabel.setVisible(false);
        }
        if (!se.isReferenceUnique(reference)) {
            referenceErrorLabel.setText("La référence doit être unique.");
            referenceErrorLabel.setVisible(true);
            referenceTF.requestFocus();
            return; // Arrêter la méthode si la référence n'est pas unique
        } else {
            referenceErrorLabel.setVisible(false);
        }
        String nom=nomTF.getText();
        if (nom.isEmpty() || !nom.matches("[a-zA-Z]+")) {
            nomErrorLabel.setText("Le nom ne doit pas être vide et doit contenir uniquement des lettres.");
            nomErrorLabel.setVisible(true);
            nomTF.requestFocus();
            return; // Arrêter la méthode si le nom est vide ou ne contient pas que des lettres
        } else {
            nomErrorLabel.setVisible(false);
        }
        Equipement equipement = new Equipement(reference, nom, categoriefixe.isSelected() ? "Fixe" : "Mobile", dateAjout, quantite, imagePath, descriptionTF.getText(), user, muni);

        se.ajouter(equipement); // Ajoutez l'équipement en utilisant votre service

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Equipement ajouté");
        alert.setContentText("L'équipement a été ajouté avec succès !");
        alert.show();
    }

    @FXML
    void navigatetoAfficherEquipementAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEquipementGui.fxml"));
            referenceTF.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    @FXML
    void selectQuantite(ActionEvent event) {
        Integer selectedQuantity = (Integer) quantiteCB.getSelectionModel().getSelectedItem();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> list = FXCollections.observableArrayList();
        for (int i = 0; i <= 20; i++) {
            list.add(i);
        }
        quantiteCB.setItems(list);
    }

    @FXML
    void telechargerImageEquipemnt(ActionEvent event) {
        telechargerimage.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"), new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png"));
            Stage stage = (Stage) telechargerimage.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                // Affiche le nom du fichier sélectionné
                imageequipement.setText(selectedFile.getName());

                // Récupère le chemin absolu du fichier
                String absolutePath = selectedFile.getAbsolutePath();
                // Stocke le chemin absolu dans la variable de classe
                imagePath = absolutePath;

                // Crée une URL à partir du chemin absolu du fichier
                String fileUrl = new File(absolutePath).toURI().toString();

                // Crée une image à partir de l'URL du fichier
                Image image = new Image(fileUrl);

                // Affiche l'image dans l'ImageView
                imagevieweq.setImage(image);
            }
        });
    }

}

