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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

    @FXML
    void AjoutEventClick(ActionEvent event) {
        try {
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
                    TFcategorie.getText()
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



}
