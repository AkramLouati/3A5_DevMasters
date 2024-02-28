package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceUser;
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

public class ModifierUser {

    @FXML
    private ImageView imageM;

    @FXML
    private TextField tfAdresse;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfTel;


    private ServiceUser serviceUser = new ServiceUser();
    private EndUser endUser;

    public void setData(EndUser endUser) {
        this.endUser = endUser;
        // Afficher les données de l'événement dans les champs de texte
        tfNom.setText(endUser.getNom());
        tfEmail.setText(endUser.getEmail());
        tfPassword.setText(endUser.getPassword());
        tfAdresse.setText(endUser.getLocation());
        tfTel.setText(endUser.getPhoneNumber());

        // Afficher l'image de l'événement
        String imagePath = endUser.getImage();
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
//        if (serviceUser == null) {
//            serviceUser = new serviceUser();
//        }

        // Récupérer les nouvelles valeurs des champs de texte
        String nom = tfNom.getText();
        String email = tfEmail.getText();
        String password = tfPassword.getText();
        String phone = tfTel.getText();
        String localisation = tfAdresse.getText();

        // Mettre à jour les données de l'événement
        endUser.setNom(nom);
        endUser.setEmail(email);
        endUser.setPassword(password);
        endUser.setPhoneNumber(phone);
        endUser.setLocation(localisation);

        // Appeler le service pour mettre à jour l'événement dans la base de données
        serviceUser.modifier(endUser);

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
            Stage stage = (Stage) tfNom.getScene().getWindow();
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
                new FileChooser.ExtensionFilter("Images", ".png", ".jpg", "*.gif")
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
                endUser.setImage(selectedFile.getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // Gérer les exceptions liées à la lecture de l'image
            }
        }
    }
    private void updateTextFieldStyles() {
        setFieldStyle(tfNom, isFieldEmpty(tfNom));
        setFieldStyle(tfEmail, isFieldEmpty(tfEmail));
        setFieldStyle(tfAdresse, isFieldEmpty(tfAdresse));
        setFieldStyle(tfTel, isFieldEmpty(tfTel));
        setFieldStyle(tfPassword, isFieldEmpty(tfPassword));
    }

    private boolean isFieldEmpty(TextField textField) {
        return textField.getText().isEmpty();
    }

    private void setFieldStyle(TextField textField, boolean isEmpty) {
        if (isEmpty) {
            textField.setStyle("-fx-background-color: red;");
        } else {
            textField.setStyle("-fx-background-color: lime;");
        }
    }

    private boolean isAnyFieldEmpty() {
        return isFieldEmpty(tfNom) || isFieldEmpty(tfEmail) || isFieldEmpty(tfAdresse) ||
                isFieldEmpty(tfTel) || isFieldEmpty(tfPassword);
    }

}



