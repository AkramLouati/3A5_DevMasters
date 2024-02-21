package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

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
}
