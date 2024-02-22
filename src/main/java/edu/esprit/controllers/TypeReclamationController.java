package edu.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TypeReclamationController {
    @FXML
    void casnonurgentAction(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page AjoutReclamation.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutReclamation.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la page chargée
            AjoutReclamation ajoutReclamationController = loader.getController();

            // Appeler la méthode pour définir le contenu de la liste en fonction du cas non urgent
            ajoutReclamationController.setTypesReclamation(false);

            // Créer une nouvelle scène avec la racine chargée
            Scene scene = new Scene(root);

            // Récupérer la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre principale
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void casurgentAction(ActionEvent event) {
        try {
            // Charger le fichier FXML de la page AjoutReclamation.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutReclamation.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la page chargée
            AjoutReclamation ajoutReclamationController = loader.getController();

            // Appeler la méthode pour définir le contenu de la liste en fonction du cas non urgent
            ajoutReclamationController.setTypesReclamation(true);

            // Créer une nouvelle scène avec la racine chargée
            Scene scene = new Scene(root);

            // Récupérer la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre principale
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

