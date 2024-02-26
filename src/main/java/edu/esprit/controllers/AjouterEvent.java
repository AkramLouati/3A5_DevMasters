package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterEvent {

    @FXML
    private TextField TFnom;

    @FXML
    private TextField TFdateDeb;

    @FXML
    private TextField TFdateFin;

    @FXML
    private TextField TFcapacite;

    @FXML
    private TextField TFcategorie;

    @FXML
    private ImageView imageID;

    private String imagePath; // Variable to store the path of the selected image

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

    @FXML
    void AjoutEventClick(ActionEvent event) {
        updateTextFieldStyles();
        try {
            // Vérifier si un champ est vide
            if (TFnom.getText().isEmpty() || TFdateDeb.getText().isEmpty() || TFdateFin.getText().isEmpty() ||
                    TFcapacite.getText().isEmpty() || TFcategorie.getText().isEmpty()) {
                showAlert("Error", "Tous les champs doivent être remplis.");
                return;
            }

            // Vérifier si le champ Capacite Max est un entier
            try {
                Integer.parseInt(TFcapacite.getText());
            } catch (NumberFormatException e) {
                showAlert("Error", "Le champ Capacite Max doit être un entier.");
                return;
            }

            // Vérifier si une image est sélectionnée
            if (imagePath == null || imagePath.isEmpty()) {
                showAlert("Error", "Veuillez sélectionner une image.");
                return;
            }
            // Check if an image is selected
            if (imagePath == null || imagePath.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please select an image.");
                alert.show();
                return;
            }

            // Récupération de l'utilisateur actuel (à remplacer par votre mécanisme d'authentification)
            ServiceUser serviceUser = new ServiceUser();
            EndUser user = serviceUser.getOneByID(12); // Exemple : suppose que l'utilisateur actuel a l'ID 1

            // Création de l'événement
            Evenement evenement = new Evenement(
                    user,
                    TFnom.getText(),
                    TFdateDeb.getText(),
                    TFdateFin.getText(),
                    Integer.parseInt(TFcapacite.getText()),
                    TFcategorie.getText(),
                    imagePath // Set the image path
            );

            // Ajout de l'événement via le service
            serviceEvenement.ajouter(evenement);

            // Affichage d'une notification de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Événement ajouté avec succès !");
            alert.show();
        } catch (SQLException | NumberFormatException e) {
            // En cas d'erreur lors de l'ajout de l'événement
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de l'ajout de l'événement : " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    void navigateOnClickk(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface à laquelle vous souhaitez naviguer
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEventS.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'un des éléments de l'interface actuelle
            Stage stage = (Stage) TFnom.getScene().getWindow();

            // Mettre la nouvelle scène sur le stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les exceptions liées au chargement de l'interface
        }
    }

    @FXML
    void browseOnClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png")
        );
        Stage stage = (Stage) imageID.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                imagePath = selectedFile.getAbsolutePath();
                Image image = new Image(selectedFile.toURI().toString());
                imageID.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load image: " + e.getMessage());
            }
        }
    }
    private void updateTextFieldStyles() {
        // Vérifier si le champ Nom Event est vide
        if (TFnom.getText().isEmpty()) {
            TFnom.setStyle("-fx-background-color: red;");
        } else {
            TFnom.setStyle("-fx-background-color: lime;");
        }

        // Vérifier si le champ Date Debut est vide
        if (TFdateDeb.getText().isEmpty()) {
            TFdateDeb.setStyle("-fx-background-color: red;");
        } else {
            TFdateDeb.setStyle("-fx-background-color: lime;");
        }

        // Vérifier si le champ Date Fin est vide
        if (TFdateFin.getText().isEmpty()) {
            TFdateFin.setStyle("-fx-background-color: red;");
        } else {
            TFdateFin.setStyle("-fx-background-color: lime;");
        }

        // Vérifier si le champ Capacite Max est vide
        if (TFcapacite.getText().isEmpty()) {
            TFcapacite.setStyle("-fx-background-color: red;");
        } else {
            TFcapacite.setStyle("-fx-background-color: lime;");
        }

        // Vérifier si le champ Categorie est vide
        if (TFcategorie.getText().isEmpty()) {
            TFcategorie.setStyle("-fx-background-color: red;");
        } else {
            TFcategorie.setStyle("-fx-background-color: lime;");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
}
