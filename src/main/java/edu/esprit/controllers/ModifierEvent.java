package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ModifierEvent {

    @FXML
    private TextField TFnomM;

    @FXML
    private TextField TFdateDebM;

    @FXML
    private TextField TFdateFinM;

    @FXML
    private TextField TFcapaciteM;

    @FXML
    private TextField TFcategorieM;

    @FXML
    private ImageView imageM;

    private ServiceEvenement serviceEvenement;
    private Evenement evenement;

    public void setData(Evenement evenement) {
        this.evenement = evenement;
        // Afficher les données de l'événement dans les champs de texte
        TFnomM.setText(evenement.getNomEvent());
        TFdateDebM.setText(evenement.getDateEtHeureDeb());
        TFdateFinM.setText(evenement.getDateEtHeureFin());
        TFcapaciteM.setText(String.valueOf(evenement.getCapaciteMax()));
        TFcategorieM.setText(evenement.getCategorie());

        // Afficher l'image de l'événement
        String imagePath = evenement.getImageEvent();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageM.setImage(image);
            }
        }
    }

    @FXML
    void ModifierEventClick(ActionEvent event) {
        updateTextFieldStyles();
        // Vérifier si le serviceEvenement est initialisé
        if (serviceEvenement == null) {
            serviceEvenement = new ServiceEvenement();
        }

        // Récupérer les nouvelles valeurs des champs de texte
        String nom = TFnomM.getText();
        String dateDeb = TFdateDebM.getText();
        String dateFin = TFdateFinM.getText();
        int capaciteMax = Integer.parseInt(TFcapaciteM.getText());
        String categorie = TFcategorieM.getText();

        // Mettre à jour les données de l'événement
        evenement.setNomEvent(nom);
        evenement.setDateEtHeureDeb(dateDeb);
        evenement.setDateEtHeureFin(dateFin);
        evenement.setCapaciteMax(capaciteMax);
        evenement.setCategorie(categorie);

        // Appeler le service pour mettre à jour l'événement dans la base de données
        serviceEvenement.modifier(evenement);

        // Afficher une alerte pour confirmer la modification
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("L'événement a été modifié avec succès.");
        alert.setTitle("Événement modifié");
        alert.show();
    }


    @FXML
    void navigateOnClick(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface AfficherEventS.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEventS.fxml"));

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton "Navigate"
            Stage stage = (Stage) TFnomM.getScene().getWindow();

            // Mettre la nouvelle scène sur le stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les exceptions liées au chargement de l'interface AfficherEventS.fxml
        }
    }

    @FXML
    void browseMOnClick(ActionEvent event) {
        // Configurer le FileChooser pour sélectionner une image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );

        // Afficher le FileChooser et récupérer le fichier sélectionné
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                // Créer une image JavaFX à partir du fichier sélectionné
                Image image = new Image(new FileInputStream(selectedFile));
                // Afficher l'image dans l'ImageView
                imageM.setImage(image);
                // Enregistrer le chemin de l'image dans l'événement
                evenement.setImageEvent(selectedFile.getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // Gérer les exceptions liées à la lecture de l'image
            }
        }
    }
    private void updateTextFieldStyles() {
        // Vérifier si le champ Nom Event est vide
        if (TFnomM.getText().isEmpty()) {
            TFnomM.setStyle("-fx-background-color: red;");
        } else {
            TFnomM.setStyle("-fx-background-color: lime;");
        }

        // Vérifier si le champ Date Debut est vide
        if (TFdateDebM.getText().isEmpty()) {
            TFdateDebM.setStyle("-fx-background-color: red;");
        } else {
            TFdateDebM.setStyle("-fx-background-color: lime;");
        }

        // Vérifier si le champ Date Fin est vide
        if (TFdateFinM.getText().isEmpty()) {
            TFdateFinM.setStyle("-fx-background-color: red;");
        } else {
            TFdateFinM.setStyle("-fx-background-color: lime;");
        }

        // Vérifier si le champ Capacite Max est vide
        if (TFcapaciteM.getText().isEmpty()) {
            TFcapaciteM.setStyle("-fx-background-color: red;");
        } else {
            TFcapaciteM.setStyle("-fx-background-color: lime;");
        }

        // Vérifier si le champ Categorie est vide
        if (TFcategorieM.getText().isEmpty()) {
            TFcategorieM.setStyle("-fx-background-color: red;");
        } else {
            TFcategorieM.setStyle("-fx-background-color: lime;");
        }
    }
}
