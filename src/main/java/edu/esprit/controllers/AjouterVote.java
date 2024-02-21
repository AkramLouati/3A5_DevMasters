package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Vote;
import edu.esprit.services.ServiceUser;
import edu.esprit.services.ServiceVote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AjouterVote {

    @FXML
    private TextField TDdesc;

    @FXML
    private TextField TFdateS;

    private final ServiceVote serviceVote = new ServiceVote();

    @FXML
    void AjouterVoteOnClick(ActionEvent event) {
        try {
            // Récupération de l'utilisateur actuel (à remplacer par votre mécanisme d'authentification)
            ServiceUser serviceUser = new ServiceUser();
            EndUser user = serviceUser.getOneByID(12); // Exemple : suppose que l'utilisateur actuel a l'ID 1

            // Création du vote
            Vote vote = new Vote(
                    user,
                    TDdesc.getText(),
                    TFdateS.getText()
            );

            // Ajout du vote via le service
            serviceVote.ajouter(vote);

            // Affichage d'une notification de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Vote ajouté avec succès !");
            alert.show();
        } catch (SQLException e) {
            // En cas d'erreur lors de l'ajout du vote
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de l'ajout du vote : " + e.getMessage());
            alert.show();
        }
    }
}
