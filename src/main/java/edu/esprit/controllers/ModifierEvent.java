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
            // Fermer la fenêtre actuelle
            Stage stage = (Stage) TFnomM.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer les exceptions liées à la fermeture de la fenêtre
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
        setFieldStyle(TFnomM, isFieldEmpty(TFnomM));
        setFieldStyle(TFdateDebM, isFieldEmpty(TFdateDebM));
        setFieldStyle(TFdateFinM, isFieldEmpty(TFdateFinM));
        setFieldStyle(TFcapaciteM, !isCapaciteValid());
        setFieldStyle(TFcategorieM, isFieldEmpty(TFcategorieM));
    }

    private boolean isFieldEmpty(TextField textField) {
        return textField.getText().isEmpty();
    }

    private boolean isCapaciteValid() {
        try {
            Integer.parseInt(TFcapaciteM.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void setFieldStyle(TextField textField, boolean isEmpty) {
        if (isEmpty) {
            textField.setStyle("-fx-background-color: red;");
        } else {
            textField.setStyle("-fx-background-color: lime;");
        }
    }

    private boolean isAnyFieldEmpty() {
        return isFieldEmpty(TFnomM) || isFieldEmpty(TFdateDebM) || isFieldEmpty(TFdateFinM) ||
                isFieldEmpty(TFcapaciteM) || isFieldEmpty(TFcategorieM);
    }
}