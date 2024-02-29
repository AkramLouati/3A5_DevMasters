package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ModifierEvent {

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

    private ServiceEvenement serviceEvenement = new ServiceEvenement();
    private Evenement evenement;

    public void setData(Evenement evenement) {
        this.evenement = evenement;
        // Afficher les données de l'événement dans les champs de texte
        TFnom.setText(evenement.getNomEvent());
        TFdateDeb.setText(evenement.getDateEtHeureDeb());
        TFdateFin.setText(evenement.getDateEtHeureFin());
        TFcapacite.setText(String.valueOf(evenement.getCapaciteMax()));
        TFcategorie.setText(evenement.getCategorie());

        // Afficher l'image de l'événement
        String imagePath = evenement.getImageEvent();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageID.setImage(image);
            }
        }
    }

    @FXML
    void ModifierEventClick(ActionEvent event) {
        updateTextFieldStyles();
        try {
            // Vérifier si tous les champs sont remplis
            if (isAnyFieldEmpty()) {
                showAlert("Error", "Tous les champs doivent être remplis.");
                return;
            }

            // Vérifier si le champ Capacite Max est un entier
            if (!isCapaciteValid()) {
                showAlert("Error", "Le champ Capacite Max doit être un entier.");
                return;
            }

            // Vérifier si la date de fin est après la date de début
            if (!isDateFinValid()) {
                showAlert("Error", "La date de fin doit être après la date de début.");
                return;
            }

            // Vérifier si une image est sélectionnée
            if (imagePath == null || imagePath.isEmpty()) {
                showAlert("Error", "Veuillez sélectionner une image.");
                return;
            }

            // Mettre à jour les données de l'événement
            evenement.setNomEvent(TFnom.getText());
            evenement.setDateEtHeureDeb(TFdateDeb.getText());
            evenement.setDateEtHeureFin(TFdateFin.getText());
            evenement.setCapaciteMax(Integer.parseInt(TFcapacite.getText()));
            evenement.setCategorie(TFcategorie.getText());
            evenement.setImageEvent(imagePath);

            // Appeler le service pour mettre à jour l'événement dans la base de données
            serviceEvenement.modifier(evenement);

            // Afficher une alerte pour confirmer la modification
            showAlert("Success", "L'événement a été modifié avec succès.");
        } catch (Exception e) {
            showAlert("Error", "Erreur lors de la modification de l'événement : " + e.getMessage());
        }
    }

    @FXML
    void navigateOnClickk(ActionEvent event) {
        // Fermer la fenêtre actuelle
        Stage stage = (Stage) TFnom.getScene().getWindow();
        stage.close();
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
                Image image = new Image(new FileInputStream(selectedFile));
                imageID.setImage(image);
            } catch (FileNotFoundException e) {
                showAlert("Error", "Failed to load image: " + e.getMessage());
            }
        }
    }

    private void updateTextFieldStyles() {
        setFieldStyle(TFnom, isFieldEmpty(TFnom));
        setFieldStyle(TFdateDeb, isFieldEmpty(TFdateDeb));
        setFieldStyle(TFdateFin, isFieldEmpty(TFdateFin) || !isDateFinValid());
        setFieldStyle(TFcapacite, !isCapaciteValid());
        setFieldStyle(TFcategorie, isFieldEmpty(TFcategorie));
    }

    private boolean isFieldEmpty(TextField textField) {
        return textField.getText().isEmpty();
    }

    private boolean isCapaciteValid() {
        try {
            Integer.parseInt(TFcapacite.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDateFinValid() {
        // Implement your date validation logic here
        return true; // Dummy implementation, replace with your logic
    }

    private void setFieldStyle(TextField textField, boolean isInvalid) {
        if (isInvalid) {
            textField.setStyle("-fx-border-color: red;");
        } else {
            textField.setStyle("-fx-border-color: lime;");
        }
    }

    private boolean isAnyFieldEmpty() {
        return isFieldEmpty(TFnom) || isFieldEmpty(TFdateDeb) || isFieldEmpty(TFdateFin) ||
                isFieldEmpty(TFcapacite) || isFieldEmpty(TFcategorie);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
}
