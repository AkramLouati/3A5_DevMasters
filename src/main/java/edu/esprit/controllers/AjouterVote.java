package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Vote;
import edu.esprit.services.ServiceUser;
import edu.esprit.services.ServiceVote;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AjouterVote {

    @FXML
    private TextField DesTF;

    @FXML
    private TextField DateTF;

    private final ServiceVote serviceVote = new ServiceVote();
    private final ServiceUser serviceUser = new ServiceUser();

    @FXML
    public void AjouterVoteAction(javafx.event.ActionEvent actionEvent) {

        try {
            // Récupérer l'utilisateur actuel (vous devrez implémenter cette logique en fonction de votre application)
            // Pour l'instant, supposons que l'ID de l'utilisateur est 1
            EndUser currentUser = serviceUser.getOneByID(1);

            // Créer l'objet vote
            Vote vote = new Vote(
                    currentUser, // Passer l'utilisateur actuel
                    DesTF.getText(),
                    DateTF.getText()
            );

            // Ajouter le vote
            serviceVote.ajouter(vote);

            // Afficher un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Vote ajouté avec succès !");
            alert.show();
        } catch (SQLException e) {
            // Afficher un message d'erreur si quelque chose ne va pas
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de l'ajout du vote : " + e.getMessage());
            alert.showAndWait();
        }
    }
}
